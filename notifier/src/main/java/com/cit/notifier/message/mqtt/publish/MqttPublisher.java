package com.cit.notifier.message.mqtt.publish;

import com.cit.notifier.message.mqtt.IMqttNotificationClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class MqttPublisher implements IMqttPublisher{

    @Autowired
    IMqttNotificationClient mqttNotificationClient;

    @Override
    public void publish(String topic, String message) {

        try {
            this.mqttNotificationClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
