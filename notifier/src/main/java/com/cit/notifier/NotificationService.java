package com.cit.notifier;

import com.cit.notifier.dto.CloneDetectionAlertDto;
import com.cit.notifier.message.mqtt.publish.IMqttPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class NotificationService implements  INotificationService{

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private IMqttPublisher publisher;

    @Override
    public void sendCloneDetectionAlert(CloneDetectionAlertDto cloneDetectionAlertDto) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            publisher.publish(MessageTopics.CLONE_DETECTION_ALERT, mapper.writeValueAsBytes(cloneDetectionAlertDto));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }
}
