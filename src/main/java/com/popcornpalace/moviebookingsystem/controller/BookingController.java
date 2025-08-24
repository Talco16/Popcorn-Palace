package com.popcornpalace.moviebookingsystem.controller;

import com.popcornpalace.moviebookingsystem.model.Booking;
import com.popcornpalace.moviebookingsystem.service.BookingService;
import com.popcornpalace.moviebookingsystem.util.reqeuest.BookingRequest;
import com.popcornpalace.moviebookingsystem.util.response.BookingResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Post request for booking ticket.
    @PostMapping
    public ResponseEntity<Booking> bookTicket(@Valid @RequestBody BookingRequest bookingRequest) {
        Booking resp = bookingService.bookTicket(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}