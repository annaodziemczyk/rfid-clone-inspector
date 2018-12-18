package com.cit.clonedetection.rulebook.remotelocation.rules;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.common.rules.CommonCloneDetectionRule;
import com.cit.locator.distance.om.TravelRoute;
import com.cit.locator.distance.service.IDistanceService;
import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by odziea on 11/28/2018.
 */
@RuleBean
@Rule(order = 1)
public class WithinFlyingDistanceRule extends CommonCloneDetectionRule {

    @Autowired
    private IDistanceService distanceService;
    private TravelRoute travelRoute;

    @Result
    protected CloneDetectionResult cloneDetectionResult;

    @When
    public boolean when() {

        travelRoute = this.distanceService.findShortestTransitRoute(this.previousAccessRequest.getAccessIssuer().getGeoLocation(), this.currentAccessRequest.getAccessIssuer().getGeoLocation(), this.previousAccessRequest.getAccessTime().toInstant());
        Duration timeSincePreviousRequest = this.durationBetweenAccessTimes();

        return travelRoute.getDurationInSeconds()>=timeSincePreviousRequest.getSeconds();
    }

    @Then
    public RuleState then() {

        this.cloneDetectionResult = new CloneDetectionResult();
        this.cloneDetectionResult.setPreviousAccessRequest(this.previousAccessRequest);
        this.cloneDetectionResult.setAccessRequest(currentAccessRequest);
        this.cloneDetectionResult.setGenuineCard(false);
        this.cloneDetectionResult.setReason(String.format("Impossible to travel between the locations within %s. It takes minimum of %s to commute.",
                this.formatDisplayTime(this.durationBetweenAccessTimes().getSeconds()), this.formatDisplayTime(travelRoute.getDurationInSeconds())));
        return RuleState.BREAK;
    }
}
