FROM openjdk:8-jdk-alpine
FROM maven:alpine

# image layer
WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests

ADD /app/target/*.jar target/*.jar store-api.jar
ENTRYPOINT ["java","-jar","store-api.jar"]