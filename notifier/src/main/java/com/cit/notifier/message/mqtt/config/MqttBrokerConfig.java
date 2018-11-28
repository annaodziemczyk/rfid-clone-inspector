package com.cit.notifier.message.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by odziea on 11/27/2018.
 */
@Component
@ConfigurationProperties(prefix = "mqtt")
@PropertySource("classpath:mqtt.properties")
public class MqttBrokerConfig {
    private String broker;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
