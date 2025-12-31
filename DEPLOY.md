# Deployment Instructions

This project is a Spring Boot application for validating NACHA files.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Building the Application

To build the application and generate an executable JAR file, run the following command in the root directory of the project:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory, typically named `parser-0.1.0.jar` (based on the `pom.xml` version).

## Running the Application

Once the build is complete, you can run the application using the following command:

```bash
java -jar target/parser-0.1.0.jar
```

By default, the application will start on port 8080.

## Accessing the Application

Open a web browser and navigate to:

http://localhost:8080

You will see the "NACHA File Validator" interface where you can upload a file to validate.

## Configuration

The application runs with a default configuration. If you need to change the port or other settings, you can pass arguments to the command line:

```bash
java -jar target/parser-0.1.0.jar --server.port=9090
```
