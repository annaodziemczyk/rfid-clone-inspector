package com.cit.clonedetection.rulebook.buildingboundaries.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.common.rules.CommonCloneDetectionRule;
import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.om.TravelRoute;
import com.cit.locator.distance.service.IDistanceService;
import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Rules to identify whether the two access request panels are within walking distance
 * given specified time constraints
 */
@RuleBean
@Rule(order = 1)
public class WithinWalkingDistanceRule extends CommonCloneDetectionRule {

    @Result
    protected CloneDetectionResult cloneDetectionResult;

    @Autowired
    private IDistanceService distanceService;

    protected final double AVG_STOREY_HIGHT_IN_FEET = 10;
    protected final long MIN_LIFT_TRANSIT_TIME_IN_MILLS = 100000;

    private int differenceInFloors;



    @When
    public boolean when() {

        GeoLocation currentLocation = this.currentAccessRequest.getAccessIssuer().getGeoLocation();
        GeoLocation previousLocation = this.previousAccessRequest.getAccessIssuer().getGeoLocation();

        double distanceInMeters = this.distanceService.calculateDistanceInMeters(previousLocation, currentLocation);
        Duration timeSincePreviousRequest = this.durationBetweenAccessTimes();

        double timeBetweenFloors = travelTimeBetweenFloors(currentLocation.getZ(), previousLocation.getZ());

        return (distanceService.calculateTravelTime(distanceInMeters, TravelMode.WALKING)+timeBetweenFloors)>=timeSincePreviousRequest.getSeconds();
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

    private double travelTimeBetweenFloors(double altitude1, double altitude2){

        this.differenceInFloors = this.floorDifference(altitude1, altitude2);;
        return TimeUnit.MILLISECONDS.toSeconds(MIN_LIFT_TRANSIT_TIME_IN_MILLS*this.differenceInFloors);
    }

    @Then
    public RuleState then() {
        this.cloneDetectionResult = new CloneDetectionResult();
        this.cloneDetectionResult.setPreviousAccessRequest(this.previousAccessRequest);
        this.cloneDetectionResult.setAccessRequest(currentAccessRequest);
        this.cloneDetectionResult.setGenuineCard(false);

        if(this.differenceInFloors>0){
            this.cloneDetectionResult.setReason(String.format("Impossible to travel between %s building floors within %s", this.differenceInFloors, this.formatDisplayTime(this.durationBetweenAccessTimes().getSeconds())));
        }else{
            this.cloneDetectionResult.setReason(String.format("Impossible to walk the distance within %s", this.formatDisplayTime(this.durationBetweenAccessTimes().getSeconds())));
        }
        return RuleState.BREAK;
    }
}
