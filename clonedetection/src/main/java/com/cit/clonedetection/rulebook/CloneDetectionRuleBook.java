package com.cit.clonedetection.rulebook;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.stereotype.Service;

/**
 * Class to define a rule book encapsulating rules designed to detect cloned rfid card intances
 */
@Service
public class CloneDetectionRuleBook extends SpringAwareRuleBookRunner implements ICloneDetectionRuleBook {

    public CloneDetectionRuleBook() {
        super("com.cit.clonedetection.rulebook.common.rules");
    }

}
