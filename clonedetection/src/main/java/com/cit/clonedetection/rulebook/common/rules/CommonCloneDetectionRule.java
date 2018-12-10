package com.cit.clonedetection.rulebook.common.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.common.facts.CloneDetectionFacts;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.device.TokenReader;
import com.cit.common.om.access.request.AccessRequest;
import com.cit.common.om.location.Address;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.annotation.Given;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by odziea on 11/28/2018.
 */
public abstract class CommonCloneDetectionRule {

    @Given("currentAccessRequest")
    protected AccessRequest currentAccessRequest;

    @Given("previousAccessRequest")
    protected AccessRequest previousAccessRequest;

    protected NameValueReferableMap<AccessRequest> getFacts(){
        CloneDetectionFacts facts = new CloneDetectionFacts(this.currentAccessRequest, this.previousAccessRequest);
        return  facts;
    }

    protected Address retrieveAddress(TokenReader tokenReader){
        Address locationAddress=new Address();

        if(tokenReader instanceof RfidReaderPanel){
            locationAddress=((RfidReaderPanel) tokenReader).getBuilding().getAddress();
        }

        return locationAddress;
    }

    protected Duration durationBetweenAccessTimes(){
        ZonedDateTime currentAccessTime = currentAccessRequest.getAccessTime();
        ZonedDateTime previousAccessTime = previousAccessRequest.getAccessTime();
        Duration duration = Duration.between(previousAccessTime, currentAccessTime);

        return duration;
    }

    protected String formatDisplayTime(long timeInSeconds){

        long hours = TimeUnit.SECONDS.toHours(timeInSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds-hours*3600);
        long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds-hours*3600-minutes*60);

        String displayTime = "";

        if(hours>0){
            displayTime += hours + "h ";
        }

        if(minutes>0 | hours>0){
            displayTime += minutes + "min ";
        }

        if(seconds>0 | (minutes==0 && hours==0)){
            displayTime += seconds + "s";
        }

        return displayTime;

    }
}
