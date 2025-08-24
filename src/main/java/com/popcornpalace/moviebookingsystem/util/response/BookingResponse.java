package com.popcornpalace.moviebookingsystem.util.response;

import java.util.UUID;

public record BookingResponse(
        Long showtimeId,
        UUID userId,
        int bookedSeat
) {}