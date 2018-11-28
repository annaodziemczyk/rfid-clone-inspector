package com.cit.clonedetection.rulebook;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/28/2018.
 */
@Service
public class CloneDetectionRuleBook extends SpringAwareRuleBookRunner implements ICloneDetectionRuleBook {

    public CloneDetectionRuleBook() {
        super("com.cit.clonedetection.rulebook.rules");
    }

}
