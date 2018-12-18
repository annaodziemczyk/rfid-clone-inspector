package com.cit.clonedetection.rulebook.common.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.remotelocation.IRemoteLocationRuleBook;
import com.cit.common.om.location.Address;
import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by odziea on 11/28/2018.
 */
@RuleBean
@Rule(order = 4)
public class RemoteLocationRule extends CommonCloneDetectionRule{

    @Autowired
    private IRemoteLocationRuleBook remoteLocationRuleBook;

    @Result
    protected com.deliveredtechnologies.rulebook.Result<CloneDetectionResult> cloneDetectionResult;

    @When
    public boolean when() {

        Address currentLocationAddress=this.retrieveAddress(currentAccessRequest.getAccessIssuer());
        Address previousLocationAddress=this.retrieveAddress(previousAccessRequest.getAccessIssuer());

        return !currentLocationAddress.getCountry().equals(previousLocationAddress.getCountry());
    }

    @Then
    public RuleState then() {
        if(this.cloneDetectionResult!=null){
            this.cloneDetectionResult.reset();
        }
        remoteLocationRuleBook.setDefaultResult(new CloneDetectionResult());
        remoteLocationRuleBook.run(this.getFacts());
        Optional<com.deliveredtechnologies.rulebook.Result<CloneDetectionResult>> cloneDetectionResult=remoteLocationRuleBook.getResult();
        if(cloneDetectionResult.isPresent()){
            this.cloneDetectionResult = cloneDetectionResult.get();
        }
        return RuleState.BREAK;
    }
}
