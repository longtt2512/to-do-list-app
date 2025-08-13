# Stage 1: Build JAR (ARM64-friendly)
FROM --platform=$BUILDPLATFORM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# Stage 2: Run application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
