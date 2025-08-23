package com.popcornpalace.moviebookingsystem.util.response;


public record ShowtimeResponse(
        Long id,
        String theater,
        LocalDateTime startTime,
        LocalDateTime endTime,
        double price,
        MovieSummary movie
) {}