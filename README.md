# rfid-clone-inspector
POC for system that will detect use of cloned RFID cards and notify about the event in real-time.

## Demo: ##
https://rfidcloneinspector.cfapps.io

## Prerequisits 
* Java 8
* Maven 

## Environment Variables
* GOOGLE_API_KEY=[your_key]
* MQTT_BROKER=tcp://[mqtt_host_ip]:[port]

## Build

mvn clean install

### Builing a module

mvn clean install -pl :modulename

### Skiping tests

mvn clean install -DskipTests=true

### Runing tests

mvn test -pl :modulename

## Deployment

There is a continous deployment setup for PCF triggered with each master branch commit.
Application can be depoyed locally by startig the RestapiApplication in IntelliJ or by running:
java -jar restapi\target\restapi-0.0.1-SNAPSHOT-exec.jar




