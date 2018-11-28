package com.cit.clonedetection.rulebook.remotelocation;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/28/2018.
 */
@Service
public class RemoteLocationRuleBook extends SpringAwareRuleBookRunner implements IRemoteLocationRuleBook {
    public RemoteLocationRuleBook() {
        super("com.cit.clonedetection.rulebook.remotelocation.rules");
    }
}
