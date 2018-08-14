#!/bin/bash
java -jar ../../code/java/smart-lab/smart-lab-app-modules/smart-lab-delegate-microservice/target/smart-lab-delegate-microservice-1.0.0-SNAPSHOT.jar --spring.application.name=smart-lab-delegate-microservice-b --spring.cloud.config.uri=http://169.254.80.50:8888
