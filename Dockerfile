FROM openjdk:17-jdk-slim AS builder

# Gradle wrapper 파일 복사
COPY gradlew .

# Gradle 프로젝트 파일 및 소스 코드 복사
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Gradle wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew bootJar


FROM openjdk:17-jdk-slim

# 빌드 스테이지에서 생성된 JAR 파일을 복사
COPY --from=builder build/libs/*.jar app.jar
COPY src/main/resources/application.yml /application.yml
# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.config.location=application.yml"]
