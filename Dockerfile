FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
COPY src/main/resources/application.yml /application.yml
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.config.location=application.yml"]