# Pozole Delivery App Deployment Guide

This project consists of a Java Spring Boot backend and a Flutter frontend.

## AWS Deployment Guide

This guide explains how to deploy the application to AWS using Docker containers.

### Prerequisites
- AWS CLI installed and configured
- Docker installed
- An AWS account

### 1. Build and Push Docker Images to Amazon ECR

1.  **Create Repositories in ECR:**
    ```bash
    aws ecr create-repository --repository-name pozole-backend
    aws ecr create-repository --repository-name pozole-frontend
    ```

2.  **Authenticate Docker to ECR:**
    ```bash
    aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <aws_account_id>.dkr.ecr.<region>.amazonaws.com
    ```

3.  **Build and Push Backend:**
    ```bash
    cd backend
    docker build -t pozole-backend .
    docker tag pozole-backend:latest <aws_account_id>.dkr.ecr.<region>.amazonaws.com/pozole-backend:latest
    docker push <aws_account_id>.dkr.ecr.<region>.amazonaws.com/pozole-backend:latest
    ```

4.  **Build and Push Frontend:**
    ```bash
    cd frontend
    docker build -t pozole-frontend .
    docker tag pozole-frontend:latest <aws_account_id>.dkr.ecr.<region>.amazonaws.com/pozole-frontend:latest
    docker push <aws_account_id>.dkr.ecr.<region>.amazonaws.com/pozole-frontend:latest
    ```

### 2. Deploy using AWS App Runner (Recommended for simplicity)

AWS App Runner provides the easiest way to run containerized web applications.

**Backend Service:**
1.  Go to the [AWS App Runner Console](https://console.aws.amazon.com/apprunner).
2.  Click **Create service**.
3.  Source: **Container registry** -> **Amazon ECR**.
4.  Browse and select the `pozole-backend` image.
5.  Deployment settings: **Automatic** (optional).
6.  Configuration:
    -   Port: `8080`
    -   Environment variables:
        -   `DB_URL`: Your RDS connection string (e.g., `jdbc:postgresql://<rds-endpoint>:5432/db`)
        -   `DB_USERNAME`: Database username
        -   `DB_PASSWORD`: Database password
7.  Create & Deploy.

**Frontend Service:**
1.  Create another App Runner service for `pozole-frontend`.
2.  Port: `80`.
3.  Create & Deploy.

**Important:** The frontend needs to know the backend URL.
-   Since the frontend is a static web app served by Nginx, the API URL is typically hardcoded or injected during build.
-   In this setup, `nginx.conf` proxies `/api/` to `http://backend:8080`.
-   For **App Runner**, this internal networking isn't automatic like in Docker Compose. You have two options:
    1.  **Update `nginx.conf`** in the container to point to the *public* App Runner URL of the backend service.
    2.  **Rebuild Frontend:** Update `frontend/lib/order_screen.dart` to use the backend's App Runner URL and rebuild the image.

### 3. Deploy using AWS ECS (Fargate)

For more control or private networking:

1.  Create an **ECS Cluster** (Networking only/Fargate).
2.  Create **Task Definitions** for backend and frontend using the ECR images.
3.  Set up an **Application Load Balancer (ALB)**.
    -   Listener 80 -> Target Group for Frontend.
    -   Listener 8080 (or path `/api/*`) -> Target Group for Backend.
4.  Run the tasks as **Services**.

---

## Local Development with Docker Compose

To run the entire stack locally:

```bash
docker-compose up --build
```

-   Frontend: `http://localhost`
-   Backend: `http://localhost:8080`
