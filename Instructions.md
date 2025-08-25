# Instructions

## ✅ Prerequisites
1. Docker & Docker Compose installed.
2. (Optional, for local non-Docker run) JDK 21, Maven 3.9+

## 📂 Project Structure (high level)
.
├─ src/main/java/...       # app code
├─ src/test/java/...       # unit & web layer tests
├─ src/main/resources/
│   └─ application.yaml    # Spring config (overridden in Docker via env vars)
├─ docker-compose.yml       # brings up Postgres + the app
├─ Dockerfile               # app image
└─ pom.xml                  # Maven build

## 🚀 Run with Docker (recommended)
1. Start everything -> docker compose up --build
- Start Postgres (service db)
- Build and start the application (service app)

2. Verify it’s up
- Health endpoint: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui.html

3. Environment (how DB is wired)
- compose.yml passes these to the app:
  SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/popcorn-palace-db
  SPRING_DATASOURCE_USERNAME: popcorn-palace
  SPRING_DATASOURCE_PASSWORD: popcorn-palace

4. Stop -> docker compose down

## 🔨 Build artifacts
1. Maven build (jar) -> ./mvnw -q clean package

2. Docker image (if needed outside compose) -> docker build -t popcorn-app:local 

## 🧪 Tests
1. Run all tests -> ./mvnw -q test
- Unit tests and web layer tests run with the test profile.
- They use H2 in memory, so no Postgres is required for tests.

## 📖 Swagger
Navigate to: http://localhost:8080/swagger-ui.html

You’ll see the endpoints grouped by controller (Movies, Showtimes, Bookings).

## 👤 Author
Created by Tal Cohen