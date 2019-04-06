FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar store-api.jar
ENTRYPOINT ["java","-jar","store-api.jar"]