FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar order-api.jar
ENTRYPOINT ["java","-jar","order-api.jar"]