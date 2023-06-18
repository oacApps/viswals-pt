# Use an official OpenJDK runtime with Java 17 as the base image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build context into the container
COPY ./publisher/target/publisher-0.0.1.jar publisher-app.jar

COPY ./publisher/src/main/resources/application.yml /config/application.yml
COPY ./publisher/src/main/resources/application-prod.yml /config/application-prod.yml
# Expose the port that the application will listen on
EXPOSE 8000

# Set the command to run the application when the container starts
CMD ["java", "-jar", "publisher-app.jar", "--spring.profiles.active=prod", "--spring.config.location=file:/config/application.yml"]


