package com.popcornpalace.moviebookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "1.0", message = "Price must be greater than 1")
    @Column(nullable = false)
    private double price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Movie movie;

    @Column(nullable = false)
    @NotBlank
    private String theater;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    public Showtime() {}

    public Showtime(int id, Movie movie, String theater, LocalDateTime startTime, LocalDateTime endTime, double price) {
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    @AssertTrue(message = "endTime must be after startTime")
    private boolean isTimeOrderValid() {
        return startTime != null && endTime != null && endTime.isAfter(startTime);
    }

    public Long getId() { return id; }

    public double getPrice() { return price; }

    public Movie getMovie() { return movie; }

    public String getTheater() { return theater; }

    public LocalDateTime getStartTime() { return startTime; }

    public LocalDateTime getEndTime() { return endTime; }

    public void setPrice(double price) { this.price = price; }

    public void setMovie(Movie movie) { this.movie = movie; }

    public void setTheater(String theater) { this.theater = theater; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}