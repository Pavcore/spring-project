FROM maven:3.8.5-openjdk-21 AS build
LABEL authors="Pavel Korchagin"
COPY src /app
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests
FROM openjdk:21-jdk
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:7432/postgres
COPY target/spring-project-0.7.jar /app/spring-project-0.7.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/spring-project-0.7.jar", "--server.port=8080"]
