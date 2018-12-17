package com.cit.notifier.message.mqtt.publish;

import com.cit.notifier.message.mqtt.IMqttNotificationClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class MqttPublisher implements IMqttPublisher{

    Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

    @Autowired
    IMqttNotificationClient mqttNotificationClient;

    @Override
    public void publish(String topic, byte[] message) {

        try {
            this.mqttNotificationClient.publish(topic, message);
        } catch (MqttException e) {
            logger.error(e.getMessage());
        }
    }
}
