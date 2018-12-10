package com.cit.clonedetection.rulebook.buildingboundaries.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.common.rules.CommonCloneDetectionRule;
import com.cit.common.om.location.GeoLocation;
import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;

import java.time.Duration;

/**
 * Created by odziea on 11/28/2018.
 */
@RuleBean
@Rule(order = 1)
public class DifferentFloorsAccessRule extends CommonCloneDetectionRule {

    protected final double AVG_STOREY_HIGHT_IN_FEET = 10;
    protected final long MIN_LIFT_TRANSIT_TIME_IN_MILLS = 100000;
    private int differenceInFloors=0;

    @Result
    protected CloneDetectionResult cloneDetectionResult;

    @When
    public boolean when() {

        GeoLocation currentGeolocation=currentAccessRequest.getAccessIssuer().getGeoLocation();
        GeoLocation previousGeolocation=previousAccessRequest.getAccessIssuer().getGeoLocation();

        this.differenceInFloors = this.floorDifference(currentGeolocation.getZ(), previousGeolocation.getZ());

        return  differenceInFloors > 0 && this.impossibleToTravel(differenceInFloors);
    }

    private boolean impossibleToTravel(int floorDifference){
        Duration timeSincePreviousRequest = this.durationBetweenAccessTimes();
        return timeSincePreviousRequest.toMillis() < ( MIN_LIFT_TRANSIT_TIME_IN_MILLS*floorDifference);
    }

    private int floorDifference(double altitude1, double altitude2){
        int floorDifference=0;

        if(altitude1!=altitude2){
            double higherAltitude = Math.max(altitude1, altitude2);
            double lowerAltitude = Math.min(altitude1, altitude2);
            floorDifference = (int)(Math.floor((higherAltitude-lowerAltitude)/AVG_STOREY_HIGHT_IN_FEET));
        }

        return floorDifference;
    }

    @Then
    public RuleState then() {
        this.cloneDetectionResult = new CloneDetectionResult();
        this.cloneDetectionResult.setPreviousAccessRequest(this.previousAccessRequest);
        this.cloneDetectionResult.setAccessRequest(currentAccessRequest);
        this.cloneDetectionResult.setGenuineCard(false);
        this.cloneDetectionResult.setReason(String.format("Impossible to travel between %s building floors within %s", this.differenceInFloors+1, this.durationBetweenAccessTimes().toString()));
        return RuleState.BREAK;
    }
}
