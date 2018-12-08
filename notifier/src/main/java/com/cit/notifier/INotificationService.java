package com.cit.notifier;

import com.cit.notifier.dto.CloneDetectionAlertDto;

/**
 * Created by odziea on 11/27/2018.
 */
public interface INotificationService {

    void sendCloneDetectionAlert(CloneDetectionAlertDto cloneDetectionAlertDto);
}
