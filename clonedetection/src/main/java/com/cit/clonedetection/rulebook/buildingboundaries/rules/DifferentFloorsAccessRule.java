package com.cit.clonedetection.rulebook.buildingboundaries.rules;

import com.cit.clonedetection.rulebook.rules.CommonCloneDetectionRule;
import com.cit.common.om.location.Address;
import com.cit.common.om.location.GeoLocation;
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

    @When
    public boolean when() {

        GeoLocation currentGeolocation=currentAccessRequest.getAccessIssuer().getGeoLocation();
        GeoLocation previousGeolocation=previousAccessRequest.getAccessIssuer().getGeoLocation();

        int differenceInFloors = this.floorDifference(currentGeolocation.getZ(), previousGeolocation.getZ());

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
            floorDifference = (int)((higherAltitude-lowerAltitude)%AVG_STOREY_HIGHT_IN_FEET);
        }

        return floorDifference;
    }

    @Then
    public void then() {

    }
}
