package com.popcornpalace.moviebookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.service.ShowtimeService;
import com.popcornpalace.moviebookingsystem.util.reqeuest.ShowtimeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = ShowtimeController.class) // השם לפי המחלקה שלך
class ShowtimeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ShowtimeService showtimeService;

    @Test
    void getById_ok() throws Exception {
        Showtime s = new Showtime();
        s.setId(5L);
        Movie m = new Movie(); m.setId(1L);
        s.setMovie(m);
        s.setTheater("Hall A");

        when(showtimeService.getShowTimeById(5L)).thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/showtimes/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.theater").value("Hall A"));
    }

    @Test
    void create_validationError_returns400() throws Exception {
        // בכוונה חסרים שדות חובה
        ShowtimeRequest bad = new ShowtimeRequest();
        bad.setMovieId(null);

        mockMvc.perform(post("/api/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_success_returns201() throws Exception {
        String body = """
    {
      "movieId": 1,
      "theater": "Hall A",
      "startTime": "2030-01-01 10:00:00",
      "endTime": "2030-01-01 12:00:00",
      "price": 20.0
    }
    """;

        // מוקים לשירות
        Showtime saved = new Showtime();
        saved.setId(10L);
        Movie m = new Movie(); m.setId(1L);
        saved.setMovie(m);
        saved.setTheater("Hall A");
        when(showtimeService.createNewShowtime(any())).thenReturn(Optional.of(saved));

        mockMvc.perform(post("/api/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/showtimes/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.theater").value("Hall A"));
    }
}