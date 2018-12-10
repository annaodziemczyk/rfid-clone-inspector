package com.cit.notifier.dto;

/**
 * Created by odziea on 12/9/2018.
 */
public abstract class AlertDto {

    private String severity;
    private String title;
    private String description;

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
}
