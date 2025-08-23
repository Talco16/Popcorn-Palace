package com.popcornpalace.moviebookingsystem.util.reqeuest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowtimeRequest {
    @NotNull(message = "Movie id is required")
    private Long movieId;

    @NotBlank(message = "Theater is required")
    private String theater;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @DecimalMin(value = "1.0", message = "Price must be at least 1.0")
    private double price;
}