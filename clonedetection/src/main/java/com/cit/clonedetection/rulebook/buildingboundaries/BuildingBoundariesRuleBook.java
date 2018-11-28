package com.cit.clonedetection.rulebook.buildingboundaries;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/28/2018.
 */
@Service
public class BuildingBoundariesRuleBook extends SpringAwareRuleBookRunner implements IBuildingBoundariesRuleBook {
    public BuildingBoundariesRuleBook() {
        super("com.cit.clonedetection.rulebook.buildingboundaries.rules");
    }
}
