package com.popcornpalace.moviebookingsystem.model.movie;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(name = "uk_movie_title", columnNames = "title") // Enforce unique titles
})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "^[A-Za-zא-ת ]+$", message = "Genre must contain English/Hebrew letters and spaces")
    private String genre;

    @Column(nullable = false)
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Column(nullable = false)
    @Min(value = 1888, message = "Release year must be valid")
    @Max(value = 2100, message = "Release year must be valid")
    private int releaseYear;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    private double rating;

    public Movie() {}

    public Movie(String title, String genre, int duration, int releaseYear, double rating) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}