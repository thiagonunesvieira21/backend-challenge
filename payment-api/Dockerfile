FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar payment-api.jar
ENTRYPOINT ["java","-jar","payment-api.jar"]