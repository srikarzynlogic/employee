# Stage 1: Build the WAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files and build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17
WORKDIR /app

# Copy the WAR file from the build stage
COPY --from=build /app/target/employemanagement-0.0.1-SNAPSHOT.war app.war

# Expose the port
EXPOSE 8080

# Run the WAR (Spring Boot treats WARs like JARs if embedded Tomcat is used)
ENTRYPOINT ["java", "-jar", "app.war"]
