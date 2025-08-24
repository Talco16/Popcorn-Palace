package com.popcornpalace.moviebookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.service.BookingService;
import com.popcornpalace.moviebookingsystem.service.MovieService;
import com.popcornpalace.moviebookingsystem.util.exception.ErrorResponse;
import com.popcornpalace.moviebookingsystem.util.reqeuest.MovieRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MovieService movieService;

//    @Test
//    void getById_ok() throws Exception {
//        Showtime s = new Showtime();
//        s.setId(5L);
//        Movie m = new Movie(); m.setId(1L);
//        s.setMovie(m);
//        s.setTheater("Hall A");
//
//        when(showtimeService.getShowTimeById(5L)).thenReturn(Optional.of(s));
//
//        mockMvc.perform(get("/api/showtimes/5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(5))
//                .andExpect(jsonPath("$.theater").value("Hall A"));
//    }
//
//    @Test
//    void create_validationError_returns400() throws Exception {
//        // חוסר שדה חובה מכוון
//        ShowtimeRequest bad = new ShowtimeRequest();
//        bad.setMovieId(null);
//
//        mockMvc.perform(post("/api/showtimes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(bad)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void create_success_returns201() throws Exception {
//        ShowtimeRequest req = new ShowtimeRequest();
//        req.setMovieId(1L);
//        req.setTheater("Hall A");
//        req.setStartTime(LocalDateTime.now().plusHours(1));
//        req.setEndTime(LocalDateTime.now().plusHours(3));
//        req.setPrice(20.0);
//
//        Showtime saved = new Showtime();
//        saved.setId(10L);
//        Movie m = new Movie(); m.setId(1L);
//        saved.setMovie(m);
//        saved.setTheater("Hall A");
//
//        when(showtimeService.createNewShowtime(any())).thenReturn(Optional.of(saved));
//
//        mockMvc.perform(post("/api/showtimes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/api/showtimes/10"))
//                .andExpect(jsonPath("$.id").value(10))
//                .andExpect(jsonPath("$.theater").value("Hall A"));
//    }
}