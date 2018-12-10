package com.cit.notifier.dto;

import com.cit.notifier.om.AlertSeverity;

/**
 * Created by odziea on 12/8/2018.
 */
public class CloneDetectionAlertDto extends AlertDto{

    private AccessEventDto currentEvent;
    private AccessEventDto previousEvent;

    public CloneDetectionAlertDto(){
        this.setSeverity(AlertSeverity.High.name());
        this.setTitle("Possible Cloned Access Card");
        this.setDescription("An access-card has been used that was very recently used in another location, indicating that is unlikely to be the same card-holder");
    }

    public AccessEventDto getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(AccessEventDto currentEvent) {
        this.currentEvent = currentEvent;
    }

    public AccessEventDto getPreviousEvent() {
        return previousEvent;
    }

    public void setPreviousEvent(AccessEventDto previousEvent) {
        this.previousEvent = previousEvent;
    }
}
