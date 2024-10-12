# Step 1: Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the jar file from the target directory into the container
COPY target/ecommerce-backend-api-0.0.1-SNAPSHOT.jar /app/ecommerce-backend-api-0.0.1-SNAPSHOT.jar

# Step 4: Expose the port that your application will run on
EXPOSE 8081

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "/app/ecommerce-backend-api-0.0.1-SNAPSHOT.jar"]
