package com.popcornpalace.moviebookingsystem.util.reqeuest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "Showtime id is required")
    private Long showtimeId;

    @NotNull
    @Positive
    private int seatNumber;

    @NotNull
    private String userId;
}