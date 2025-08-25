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
# App: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
# OpenAPI:    http://localhost:8080/v3/api-docs

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

## 👤 Author
Created by Tal Cohen