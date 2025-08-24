package com.popcornpalace.moviebookingsystem.service;

import com.popcornpalace.moviebookingsystem.model.Booking;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.repository.BookingRepository;
import com.popcornpalace.moviebookingsystem.repository.ShowtimeRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.BookingRequest;
import com.popcornpalace.moviebookingsystem.util.response.BookingResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          ShowtimeRepository showtimeRepository) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
    }

    @Transactional
    public Booking bookTicket(@Valid BookingRequest bookingRequest) {
        Showtime showtime = showtimeRepository.findByIdWithLock(bookingRequest.getShowtimeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Showtime not found: " + bookingRequest.getShowtimeId()));

        int seat = bookingRequest.getSeatNumber();
        String userId = bookingRequest.getUserId().trim();

        if (bookingRepository.existsByShowtime_IdAndSeatNumber(showtime.getId(), seat)) {
            throw new IllegalStateException("Seat already booked: " + seat);
        }

        Booking booking = new Booking();
        booking.setShowtime(showtime);
        booking.setSeatNumber(seat);
        booking.setUserId(UUID.fromString(bookingRequest.getUserId()));

        try {
            return bookingRepository.saveAndFlush(booking);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Seat already booked: " + seat, e);
        }
    }
}