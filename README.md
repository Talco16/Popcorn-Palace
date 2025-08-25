# 🍿🎬 Popcorn Palace 🎬🍿

## Project Overview
This is **Popcorn Palace** - Movie Ticket Booking System “Popcorn Palace”- Backend Development.

This system is build as RESTful API for a movie ticket booking system using **Spring Boot**. 

The system manages:
- 🎥 Movies
- 🕒 Showtimes
- 🎟️ Ticket Bookings

All APIs are documented and testable via **Swagger UI**.

---

## Run (Docker – recommended)
docker compose up --build
App: http://localhost:8080
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI:    http://localhost:8080/v3/api-docs

Stop:
docker compose down
# remove DB volume:
# docker compose down -v

## Run locally (optional)
1. Set DB in application.yaml.
2. ./mvnw spring-boot:run

## Build & Test
./mvnw clean package   # build jar
./mvnw test    

## Endpoints
1. Movies: /api/movies
2. Showtimes: /api/showtimes
3. Bookings: /api/bookings

Explore them via Swagger UI at /swagger-ui.html.

## API Description
1. Error model - All error responses use this schema:
   {
   "timestamp": "2025-08-24T12:34:56Z",
   "status": 400,
   "error": "Validation Failed",
   "message": "Request validation failed",
   "path": "/api/.../....",
   "details": [
   { "field": "startTime", "issue": "must be in the future" }
   ]
   }

2. Movies:
- Base path: /api/movies
- GET /api/movies → 200 OK → Returns list of movies.
  [
  {"id":1,"title":"Inception","genre":"Sci-Fi","duration":148,"rating":8.8,"releaseYear":2010}
  ]
- POST /api/movies → 201 Created 
Body: {"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
Response: {"id":10,"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
- PUT /api/movies/{id} → 200 OK
Body: {"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
Response: {"id":10,"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
404/400 → ErrorResponse
- DELETE /api/movies/{id} → 204 No Content
400 → ErrorResponse

3. Showtimes:
- Base path: /api/showtimes
- GET /api/showtimes/{id} → 200 OK
{"id":7,"movie":{"id":1},"theater":"Hall A","startTime":"2030-01-01T10:00:00Z","endTime":"2030-01-01T12:28:00Z","price":25.0}
- POST /api/showtimes → 201 Created
  Body: {"movieId":1, "theater":"Hall A","startTime":"2030-01-01T10:00:00Z","endTime":"2030-01-01T12:28:00Z","price":25.0}
  Response: showtime object
- PUT /api/showtime/{id} → 201 Created
  Body: {"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
  Response: {"id":10,"title":"Oppenheimer","genre":"Drama","duration":180,"rating":8.6,"releaseYear":2023}
  400/409 → ErrorResponse (e.g., invalid times / overlap)
- DELETE /api/showtime/{id} → 204 No Content
  400 → ErrorResponse

4. Bookings:
- Base path: /api/bookings
- POST /api/bookings → 201 Created
  Body: {"showtimeId":7,"userId":"a3f5c6d7-1111-2222-3333-444444444444","seatNumber":11}
  Response: {"id":123,"showtimeId":7,"userId":"a3f5c6d7-1111-2222-3333-444444444444","seatNumber":11}
  404 → ErrorResponse (showtime not found)
  409 → ErrorResponse (seat already booked)
  400 → ErrorResponse (validation)

## 👤 Author
Created by Tal Cohen