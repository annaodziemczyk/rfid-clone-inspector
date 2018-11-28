package com.cit.clonedetection.rulebook.remotelocation.rules;

import com.cit.clonedetection.rulebook.rules.CommonCloneDetectionRule;
import com.cit.locator.distance.service.IDistanceService;
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
public class WithinFlyingDistanceRule extends CommonCloneDetectionRule {

    @Autowired
    private IDistanceService distanceService;


    @When
    public boolean when() {
        return true;
    }

    @Then
    public void then() {

    }
}
