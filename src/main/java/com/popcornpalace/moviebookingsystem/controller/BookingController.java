package com.popcornpalace.moviebookingsystem.controller;

import com.popcornpalace.moviebookingsystem.model.Booking;
import com.popcornpalace.moviebookingsystem.service.BookingService;
import com.popcornpalace.moviebookingsystem.util.exception.ErrorResponse;
import com.popcornpalace.moviebookingsystem.util.reqeuest.BookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Book ticket", description = "Books a ticket for a showtime")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "409", description = "Seat already booked",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Booking> bookTicket(@Valid @RequestBody BookingRequest bookingRequest) {
        Booking resp = bookingService.bookTicket(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}