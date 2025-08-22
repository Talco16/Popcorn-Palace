package com.popcornpalace.moviebookingsystem.controllers;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.services.BookingService;
import com.popcornpalace.moviebookingsystem.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * BookingController is responsible for managing ticket booking in the system.
 * It provides a RESTful API.
 * Each operation is mapped to the conventional HTTP methods in REST:
 * POST for create new ticket booking.
 * Created by Tal Cohen.
 */

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(MovieService movieService) {
        this.bookingService = bookingService;
    }

    // Post request for booking ticket.
    @PostMapping
    public Movie bookTicket(@RequestBody Movie movie) {
        return bookingService.createNewMovie(movie);
    }
}