# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Create app directory inside container
WORKDIR /app

# Copy WAR file from host to container
COPY target/employemanagement-0.0.1-SNAPSHOT.war app.war

# Expose port
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "app.war"]
