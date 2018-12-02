package com.cit.clonedetection.service;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.common.om.access.request.AccessRequest;

/**
 * Created by odziea on 11/12/2018.
 */

public interface ICloneDetectionService {

    CloneDetectionResult checkForClonedCard(AccessRequest accessRequest);
}
