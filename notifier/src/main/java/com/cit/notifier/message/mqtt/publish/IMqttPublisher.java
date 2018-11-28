package com.cit.notifier.message.mqtt.publish;

/**
 * Created by odziea on 11/27/2018.
 */
public interface IMqttPublisher {

    void publish(String topic, String message);
}
