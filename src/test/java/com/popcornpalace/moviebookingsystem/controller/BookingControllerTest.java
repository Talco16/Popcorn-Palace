package com.popcornpalace.moviebookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popcornpalace.moviebookingsystem.model.Booking;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.service.BookingService;
import com.popcornpalace.moviebookingsystem.util.exception.GlobalExceptionHandler;
import com.popcornpalace.moviebookingsystem.util.reqeuest.BookingRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@WebMvcTest(controllers = BookingController.class)
@Import({BookingControllerTest.MockConfig.class, GlobalExceptionHandler.class})
public class BookingControllerTest {
    @TestConfiguration
    static class MockConfig {
        @Bean
        BookingService bookingService() {
            return Mockito.mock(BookingService.class);
        }
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookingService bookingService;

    @Test
    void bookTicket_success_returns201_andBody() throws Exception {
        BookingRequest req = new BookingRequest();
        req.setShowtimeId(7L);
        req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
        req.setSeatNumber(11);

        // and service response
        Booking saved = new Booking();
        saved.setId(100L);
        Showtime st = new Showtime(); st.setId(7L);
        saved.setShowtime(st);
        saved.setSeatNumber(11);
        saved.setUserId(UUID.fromString(req.getUserId()));

        when(bookingService.bookTicket(any(BookingRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.seatNumber").value(11))
                .andExpect(jsonPath("$.userId").exists());
    }

    @Test
    void bookTicket_conflict_returns409_withErrorResponse() throws Exception {
        BookingRequest req = new BookingRequest();
        req.setShowtimeId(7L);
        req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
        req.setSeatNumber(11);

        when(bookingService.bookTicket(any(BookingRequest.class)))
                .thenThrow(new IllegalStateException("Seat already booked: 11"));

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Seat already booked: 11"))
                .andExpect(jsonPath("$.path").value("/api/bookings"));
    }

    @Test
    void bookTicket_validationError_returns400() throws Exception {
        // missing fields (seatNumber לא חוקי, userId חסר)
        String badJson = """
        {"showtimeId":7,"seatNumber":0}
        """;

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}