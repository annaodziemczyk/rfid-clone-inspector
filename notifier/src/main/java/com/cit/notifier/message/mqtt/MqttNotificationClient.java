package com.cit.notifier.message.mqtt;

import com.cit.notifier.message.mqtt.config.MqttBrokerConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by odziea on 11/27/2018.
 */
@Service
public class MqttNotificationClient implements IMqttClient, IMqttNotificationClient {

    @Autowired
    MqttBrokerConfig mqttBrokerConfig;

    private MqttClient mqttClient;

    private MqttConnectOptions mqttConnectOptions;

    @PostConstruct
    public void init() throws MqttException {
        if(this.mqttBrokerConfig!=null && this.mqttBrokerConfig.getBroker()!=null)
        {
            this.mqttClient = new MqttClient(this.mqttBrokerConfig.getBroker(), this.mqttClient.generateClientId());

            this.connect();
        }

    }

    @Override
    public void connect() throws MqttSecurityException, MqttException {

        this.mqttConnectOptions = new MqttConnectOptions();
        this.mqttConnectOptions.setCleanSession(true);
        this.mqttConnectOptions.setConnectionTimeout(10);

//        this.connect(this.mqttConnectOptions);

    }

    @Override
    public void connect(MqttConnectOptions mqttConnectOptions) throws MqttSecurityException, MqttException {
        this.mqttClient.connect(mqttConnectOptions);
    }

    @Override
    public void disconnect() throws MqttException {
        this.mqttClient.disconnect();
    }

    @Override
    public void disconnect(long l) throws MqttException {
        this.mqttClient.disconnect();
    }

    @Override
    public void setCallback(MqttCallback mqttCallback) {
        this.mqttClient.setCallback(mqttCallback);
    }

    @Override
    public MqttTopic getTopic(String s) {
        return this.mqttClient.getTopic(s);
    }

    @Override
    public boolean isConnected() {
        return this.mqttClient.isConnected();
    }

    @Override
    public String getClientId() {
        return this.mqttClient.getClientId();
    }

    @Override
    public String getServerURI() {
        return this.mqttClient.getServerURI();
    }

    @Override
    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.mqttClient.getPendingDeliveryTokens();
    }

    @Override
    public void close() throws MqttException {
        this.mqttClient.close();
    }

    @Override
    public void subscribe(String s) throws MqttException, MqttSecurityException {
        this.mqttClient.subscribe(s);
    }

    @Override
    public void subscribe(String[] topicFilters) throws MqttException {
        this.mqttClient.subscribe(topicFilters);
    }

    @Override
    public void subscribe(String topicFilter, int qos) throws MqttException {
        this.mqttClient.subscribe(topicFilter, qos);
    }

    @Override
    public void subscribe(String[] topicFilters, int[] qos) throws MqttException {
        this.mqttClient.subscribe(topicFilters, qos);
    }

    @Override
    public void unsubscribe(String topicFilter) throws MqttException {
        this.mqttClient.unsubscribe(topicFilter);
    }

    @Override
    public void unsubscribe(String[] topicFilters) throws MqttException {
        this.mqttClient.unsubscribe(topicFilters);
    }

    public void publish(String topic, String message) throws MqttException {
        if(!this.mqttClient.isConnected()){
            this.mqttClient.connect();
        }
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(2);
        mqttMessage.setRetained(true);
        this.publish(topic, mqttMessage);
    }

    @Override
    public void publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException, MqttPersistenceException {
        this.mqttClient.publish(topic, payload, qos, retained);
    }

    @Override
    public void publish(String topic, MqttMessage mqttMessage) throws MqttException, MqttPersistenceException {
        this.mqttClient.publish(topic, mqttMessage);
    }
}
