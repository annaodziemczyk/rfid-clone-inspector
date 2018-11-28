package com.cit.clonedetection.rulebook.cityboundaries;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/28/2018.
 */
@Service
public class CityBoundariesRuleBook extends SpringAwareRuleBookRunner implements ICityBoundariesRuleBook {

    public CityBoundariesRuleBook() {
        super("com.cit.clonedetection.rulebook.cityboundaries.rules");
    }
}
