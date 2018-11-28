package com.cit.clonedetection.rulebook.rules;

import com.cit.clonedetection.rulebook.facts.CloneDetectionFacts;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.device.TokenReader;
import com.cit.common.om.access.request.AccessRequest;
import com.cit.common.om.location.Address;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.annotation.Given;

import java.time.Duration;
import java.time.ZonedDateTime;

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
        Duration duration = Duration.between(currentAccessTime, previousAccessTime);

        return duration;
    }
}
