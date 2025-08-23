package com.popcornpalace.moviebookingsystem.Util.Reqeuest;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "^[A-Za-zא-ת ]+$", message = "Genre must contain English/Hebrew letters and spaces")
    private String genre;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Min(value = 1888, message = "Release year must be valid")
    @Max(value = 2100, message = "Release year must be valid")
    private int releaseYear;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    private double rating;
}