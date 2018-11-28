package com.cit.notifier;

import com.cit.notifier.message.mqtt.publish.IMqttPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class NotificationService implements  INotificationService{

    @Autowired
    private IMqttPublisher publisher;

    @Override
    public void sendNotification() {
        publisher.publish("TestTopic", "test message");
    }
}
