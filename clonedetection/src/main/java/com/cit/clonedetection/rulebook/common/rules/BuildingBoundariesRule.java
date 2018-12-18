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

import javax.swing.text.html.Option;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Rule identifying whether two access requests for a single card occurred within a building boundaries
 */
@RuleBean
@Rule(order = 1)
public class BuildingBoundariesRule extends CommonCloneDetectionRule {

    @Autowired
    private IBuildingBoundariesRuleBook buildingBoundariesRuleBook;

    @Result
    protected com.deliveredtechnologies.rulebook.Result<CloneDetectionResult> cloneDetectionResult;

    /**
     * If both latitude and longitude match up 5 decimal places assume the two access requests have occurred in the same building
     * @return
     */
    @When
    public boolean when() {

        GeoLocation currentLocation=this.currentAccessRequest.getAccessIssuer().getGeoLocation();
        GeoLocation previousLocation=previousAccessRequest.getAccessIssuer().getGeoLocation();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        return decimalFormat.format(currentLocation.getX()).equals(decimalFormat.format(previousLocation.getX()))
                && decimalFormat.format(currentLocation.getY()).equals(decimalFormat.format(previousLocation.getY()));
    }

    /**
     * If two access requests occurred within building boundaries process clone detection rules for the building
     * @return
     */
    @Then
    public RuleState then() {
        if(this.cloneDetectionResult!=null){
            this.cloneDetectionResult.reset();
        }
        buildingBoundariesRuleBook.setDefaultResult(new CloneDetectionResult());
        buildingBoundariesRuleBook.run(this.getFacts());
        Optional<com.deliveredtechnologies.rulebook.Result<CloneDetectionResult>>cloneDetectionResult=buildingBoundariesRuleBook.getResult();
        if(cloneDetectionResult.isPresent()){
            this.cloneDetectionResult = cloneDetectionResult.get();
        }
        return RuleState.BREAK;
    }

}
