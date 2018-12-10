package com.cit.clonedetection.rulebook.common.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.buildingboundaries.IBuildingBoundariesRuleBook;
import com.cit.common.om.location.GeoLocation;
import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by odziea on 11/28/2018.
 */
@RuleBean
@Rule(order = 1)
public class BuildingBoundariesRule extends CommonCloneDetectionRule {

    @Autowired
    private IBuildingBoundariesRuleBook buildingBoundariesRuleBook;

    @Result
    protected com.deliveredtechnologies.rulebook.Result<CloneDetectionResult> cloneDetectionResult;

    @When
    public boolean when() {

        GeoLocation currentLocation=this.currentAccessRequest.getAccessIssuer().getGeoLocation();
        GeoLocation previousLocation=previousAccessRequest.getAccessIssuer().getGeoLocation();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        return decimalFormat.format(currentLocation.getX()).equals(decimalFormat.format(previousLocation.getX()))
                && decimalFormat.format(currentLocation.getY()).equals(decimalFormat.format(previousLocation.getY()));    }


    @Then
    public RuleState then() {
        buildingBoundariesRuleBook.run(this.getFacts());
        if(buildingBoundariesRuleBook.getResult().isPresent()){
            this.cloneDetectionResult = new com.deliveredtechnologies.rulebook.Result<>();
            this.cloneDetectionResult.setValue(((CloneDetectionResult)buildingBoundariesRuleBook.getResult().get()));
        }
        return RuleState.BREAK;
    }

}
