package com.cit.clonedetection.rulebook.cityboundaries.rules;

import com.cit.clonedetection.rulebook.common.rules.CommonCloneDetectionRule;
import com.cit.locator.distance.om.TravelRoute;
import com.cit.locator.distance.service.IDistanceService;
import com.cit.locator.distance.utils.TimeUtils;
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
public class WithinDrivingDistanceRule extends CommonCloneDetectionRule {

    @Autowired
    private IDistanceService distanceService;

    private TravelRoute travelRoute;

    @When
    public boolean when() {

        travelRoute = this.distanceService.findShortestDrivingRoute(this.previousAccessRequest.getAccessIssuer().getGeoLocation(), this.currentAccessRequest.getAccessIssuer().getGeoLocation(), this.previousAccessRequest.getAccessTime().toInstant());
        Duration timeSincePreviousRequest = this.durationBetweenAccessTimes();

        return travelRoute.getDurationInSeconds()>=timeSincePreviousRequest.getSeconds();
    }

    @Then
    public void then() {
        long minutes = TimeUnit.SECONDS.toHours(travelRoute.getDurationInSeconds());

        this.cloneDetectionResult.setGenuineCard(false);
        this.cloneDetectionResult.setReason(String.format("Impossible to drive between the locations within %s. It takes minimum of %s minutes to drive.",
                this.durationBetweenAccessTimes().toString(), Long.valueOf(minutes)));
    }
}
