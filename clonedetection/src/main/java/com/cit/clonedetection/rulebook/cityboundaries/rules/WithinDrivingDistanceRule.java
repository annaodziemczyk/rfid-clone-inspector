package com.cit.clonedetection.rulebook.cityboundaries.rules;

import com.cit.clonedetection.rulebook.rules.CommonCloneDetectionRule;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.locator.distance.service.IDistanceService;
import com.cit.locator.panellocator.IPanelLocatorService;
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
public class WithinDrivingDistanceRule extends CommonCloneDetectionRule {

    @Autowired
    private IDistanceService distanceService;



    @When
    public boolean when() {


//        Distance distance = this.distanceService.execute(currentAccessReaderPanel.getGeoLocation(), previousAccessReaderPanel.getGeoLocation(), TravelMeans.driving);
        return true;
    }

    @Then
    public void then() {

    }
}
