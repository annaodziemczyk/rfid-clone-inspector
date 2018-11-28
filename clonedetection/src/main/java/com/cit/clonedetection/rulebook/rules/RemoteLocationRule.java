package com.cit.clonedetection.rulebook.rules;

import com.cit.clonedetection.rulebook.remotelocation.IRemoteLocationRuleBook;
import com.cit.common.om.location.Address;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by odziea on 11/28/2018.
 */
@RuleBean
@Rule(order = 3)
public class RemoteLocationRule extends CommonCloneDetectionRule{

    @Autowired
    private IRemoteLocationRuleBook remoteLocationRuleBook;

    @When
    public boolean when() {

        Address currentLocationAddress=this.retrieveAddress(currentAccessRequest.getAccessIssuer());
        Address previousLocationAddress=this.retrieveAddress(previousAccessRequest.getAccessIssuer());

        return !currentLocationAddress.getCountry().equals(previousLocationAddress.getCountry());
    }

    @Then
    public void then() {
        remoteLocationRuleBook.run(this.getFacts());
    }
}
