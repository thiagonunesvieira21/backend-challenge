FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar zuul-server.jar
ENTRYPOINT ["java","-jar","zuul-server.jar"]