@echo off
java -jar ../../code/java/smart-lab/smart-lab-app-modules/smart-lab-config-microservice/target/smart-lab-config-microservice-1.0.0-SNAPSHOT.jar --spring.cloud.config.server.git.uri=file://${user.home}/smart-lab --spring.cloud.config.server.git.searchPaths=configs/monolith_kurt_goedel_feedback_session