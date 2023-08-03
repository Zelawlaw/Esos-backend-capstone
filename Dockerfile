# Use the official Azul Zulu OpenJDK 17 image as the base image
FROM azul/zulu-openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY build/libs/esos-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to allow external access
EXPOSE 8080

# Define the command to run your application when the container starts
CMD ["java", "-jar", "app.jar"]

