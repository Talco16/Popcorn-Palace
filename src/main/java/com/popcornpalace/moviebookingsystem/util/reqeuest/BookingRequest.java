package com.popcornpalace.moviebookingsystem.util.reqeuest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "Showtime id is required")
    private Long showtimeId;

    @NotBlank(message = "Seat number is required")
    private int seatNumber;

    @NotBlank
    private String userId;
}