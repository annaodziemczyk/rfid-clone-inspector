package com.cit.clonedetection.rulebook.rules;

import com.cit.clonedetection.rulebook.buildingboundaries.IBuildingBoundariesRuleBook;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.device.TokenReader;
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
@Rule(order = 1)
public class BuildingBoundariesRule extends CommonCloneDetectionRule {

    @Autowired
    private IBuildingBoundariesRuleBook buildingBoundariesRuleBook;

    @When
    public boolean when() {

        Address currentLocationAddress=this.retrieveAddress(currentAccessRequest.getAccessIssuer());
        Address previousLocationAddress=this.retrieveAddress(previousAccessRequest.getAccessIssuer());

        return currentLocationAddress.equals(previousLocationAddress);
    }

    @Then
    public void then() {
        buildingBoundariesRuleBook.run(this.getFacts());
    }
}
