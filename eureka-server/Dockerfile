FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]