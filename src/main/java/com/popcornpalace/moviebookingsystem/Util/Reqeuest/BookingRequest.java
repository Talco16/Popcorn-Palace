package com.popcornpalace.moviebookingsystem.Util.Reqeuest;

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
    private String seatNumber;

    @NotBlank
    private String userId;
}