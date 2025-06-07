# Stage 1: Build the app using Maven (with JDK 17)
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application jar, skip tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Run the app using lightweight JDK 17 image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR with the exact name
COPY --from=build /app/target/employemanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
