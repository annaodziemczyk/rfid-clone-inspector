package com.cit.notifier.dto;

/**
 * Created by odziea on 12/8/2018.
 */
public class CloneDetectionAlertDto {

    private String severity;
    private String title;
    private String description;
    private AccessEventDto currentEvent;
    private AccessEventDto previousEvent;

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
