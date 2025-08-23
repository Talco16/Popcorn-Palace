package com.popcornpalace.moviebookingsystem.util.response;

public record BookingResponse(
        Long showtimeId,
        String userId,
        int bookedSeat
) {}