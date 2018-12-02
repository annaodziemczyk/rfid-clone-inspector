package com.cit.clonedetection.service;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.rulebook.ICloneDetectionRuleBook;
import com.cit.clonedetection.rulebook.common.facts.CloneDetectionFacts;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.request.AccessRequest;
import com.cit.common.om.access.token.RfidBadge;
import com.cit.locator.panellocator.IPanelLocatorService;
import com.cit.notifier.INotificationService;
import com.deliveredtechnologies.rulebook.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by odziea on 11/12/2018.
 */
@Service
public class CloneDetectionService implements ICloneDetectionService {

    @Autowired
    IPanelLocatorService panelLocatorService;

    @Autowired
    IAccessRequestManager accessRequestManager;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICloneDetectionRuleBook cloneDetectionRuleBook;


    @Autowired
    INotificationService notificationService;

    @Override
    public CloneDetectionResult checkForClonedCard(AccessRequest accessRequest) {


        CloneDetectionResult validationRulesResult = new CloneDetectionResult();

        RfidReaderPanel currentAccessReaderPanel = panelLocatorService.findPanelById(accessRequest.getAccessIssuer().getId());
        accessRequest.setAccessIssuer(currentAccessReaderPanel);

        AccessRequest<RfidBadge, RfidReaderPanel> previousAccessRequest = this.accessRequestManager.findPreviousAccessRequestForCard(accessRequest.getAccessToken().getTokenId());

        if(previousAccessRequest!=null){

            CloneDetectionFacts facts = new CloneDetectionFacts(accessRequest, previousAccessRequest);
            cloneDetectionRuleBook.run(facts);

            Optional<Result> cloneDetectionResult = cloneDetectionRuleBook.getResult();

            if (cloneDetectionResult.isPresent()) {

                CloneDetectionResult result = (CloneDetectionResult) cloneDetectionResult.get().getValue();
                validationRulesResult.setReason(result.getReason());
                validationRulesResult.setGenuineCard(result.isGenuineCard());

                if(!validationRulesResult.isGenuineCard()){
                    notificationService.sendNotification();
                }
            }else{
                validationRulesResult.setReason("Possible time-distance event.");
                validationRulesResult.setGenuineCard(true);
            }

        }else{
            validationRulesResult.setReason("Possible time-distance event.");
            validationRulesResult.setGenuineCard(true);
        }

        this.accessRequestManager.recordAccessRequest(accessRequest);

        validationRulesResult.setAccessRequest(accessRequest);
        validationRulesResult.setPreviousAccessRequest(previousAccessRequest);
        return validationRulesResult;
    }
}
