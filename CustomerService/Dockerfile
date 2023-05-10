# Use the official Maven/Java image as the base image
FROM maven:3.8.2-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package

# Use the official OpenJDK 17 image as the runtime image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/CustomerService-0.0.1-SNAPSHOT.jar CustomerService.jar

# Expose the port for the Spring application
EXPOSE 3030

# Start the Spring application
CMD ["java", "-jar", "CustomerService.jar"]
