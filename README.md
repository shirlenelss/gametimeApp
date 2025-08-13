# gametimeApp

A Spring Boot application using PostgreSQL as the database, managed with Docker.

## Prerequisites

- Java 17+
- Maven
- Docker Desktop (running)

## Getting Started

### 1. Clone the repository

```bash
git clone git@github.com:shirlenelss/gametimeApp.git
cd gametimeApp
```

## How to Run the Program

### Backend (Spring Boot + PostgreSQL)

1. **Start PostgreSQL with Docker:**
   - Make sure Docker Desktop is running.
   - In the project root, run:
     ```
     docker-compose up -d
     ```
   - This will start a PostgreSQL database on port 5432 with the credentials set in `docker-compose.yml` (production).
   - For testing, use:
     ```
     docker-compose -f docker-compose.test.yml up -d
     ```
   - This will start a PostgreSQL test database on port 5433 with the credentials set in `docker-compose.test.yml`.

2. **Build and Run the Spring Boot Backend:**
   - Make sure you have Java 17+ and Maven installed.
   - In the project root, run:
     ```
     mvn clean install
     mvn spring-boot:run
     ```
   - The backend will start on port 8080.

### Frontend (React)

1. **Install dependencies:**
   - Navigate to the frontend directory:
     ```
     cd src/main/dashboard
     npm install
     ```

2. **Start the React app:**
   - Run:
     ```
     npm start
     ```
   - The frontend will be available at [http://localhost:3000](http://localhost:3000).

## API Documentation

Once the application is running, you can access the Swagger UI for interactive API documentation at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## User stories
a. We want to add users with roles of CHILD and PARENT
b. CHILD can request time from PARENT
c. PARENT can approve or reject the request and set the time
d. PARENT can set a max time allowed per day and week

## Docker Compose Files

- `docker-compose.yml`: Used for production PostgreSQL database (port 5432).
- `docker-compose.test.yml`: Used for test PostgreSQL database (port 5433).
