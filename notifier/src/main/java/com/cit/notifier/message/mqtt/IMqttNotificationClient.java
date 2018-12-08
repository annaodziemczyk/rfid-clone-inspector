package com.cit.notifier.message.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by odziea on 11/27/2018.
 */
public interface IMqttNotificationClient {

    void publish(String topic, byte[] message) throws MqttException;
}
