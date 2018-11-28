package com.cit.notifier.message.mqtt.subscribe;

import com.cit.notifier.message.mqtt.IMqttNotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class MqttSubscriber {

    @Autowired
    IMqttNotificationClient mqttNotificationClient;

}
