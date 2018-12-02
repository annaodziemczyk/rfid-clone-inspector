package com.cit.clonedetection.rulebook.common.facts;

import com.cit.common.om.access.request.AccessRequest;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;

/**
 * Created by odziea on 11/28/2018.
 */
public class CloneDetectionFacts extends FactMap {
    public CloneDetectionFacts(AccessRequest currentAccessRequest, AccessRequest previousAccessRequest) {
        super();

        NameValueReferableMap<AccessRequest> facts = new FactMap<>();
        facts.setValue("currentAccessRequest", currentAccessRequest);
        facts.setValue("previousAccessRequest", previousAccessRequest);
        this.putAll(facts);
    }

}
