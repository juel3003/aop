# Pozole Delivery App Deployment Guide

This project consists of a Java Spring Boot backend and a Flutter frontend.

## Backend (Spring Boot)

The backend is located in the `backend/` directory.

### Prerequisites
- Java 17
- Maven

### Local Development
To run the application locally with an in-memory H2 database:
```bash
cd backend
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.

### Production Build
1. **Build the JAR:**
   ```bash
   cd backend
   mvn clean package
   ```
   The artifact will be created at `backend/target/delivery-0.0.1-SNAPSHOT.jar`.

2. **Run the JAR:**
   ```bash
   java -jar backend/target/delivery-0.0.1-SNAPSHOT.jar
   ```

3. **Configuration:**
   - Update `src/main/resources/application.properties` to use a persistent database (e.g., PostgreSQL or MySQL) instead of H2 for production.
   - Example for PostgreSQL:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/pozoledb
     spring.datasource.username=dbuser
     spring.datasource.password=dbpass
     spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
     ```

## Frontend (Flutter)

The frontend is located in the `frontend/` directory.

### Prerequisites
- Flutter SDK (latest stable)

### Configuration
Before building for production, update the API URL in `lib/order_screen.dart`.
Currently, it points to `http://localhost:8080`. For production, replace this with your deployed backend URL.

### Build for Web
1. **Build:**
   ```bash
   cd frontend
   flutter build web
   ```
   The static files will be generated in `frontend/build/web/`.

2. **Deploy:**
   - Serve the contents of `frontend/build/web/` using a web server like Nginx, Apache, or a hosting service (Firebase Hosting, Vercel, Netlify).

### Build for Android
1. **Build APK:**
   ```bash
   cd frontend
   flutter build apk --release
   ```
   The APK will be located at `frontend/build/app/outputs/flutter-apk/app-release.apk`.

### Build for iOS
(Requires macOS and Xcode)
1. **Build IPA:**
   ```bash
   cd frontend
   flutter build ipa --release
   ```

## Docker (Optional)

You can containerize the backend using a `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/delivery-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```
