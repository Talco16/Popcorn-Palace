package com.popcornpalace.moviebookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.service.MovieService;
import com.popcornpalace.moviebookingsystem.util.reqeuest.MovieRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    MovieService movieService;

    @Test
    void getAllMovies_returns200WithList() throws Exception {
        Movie m1 = new Movie(); m1.setId(1L); m1.setTitle("A");
        Movie m2 = new Movie(); m2.setId(2L); m2.setTitle("B");
        when(movieService.getAllMovies()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].title").value("B"));
    }

    @Test
    void createNewMovie_success_returns201AndLocation() throws Exception {
        MovieRequest req = new MovieRequest();
        req.setTitle("New");
        req.setGenre("Drama");
        req.setDuration(100);
        req.setReleaseYear(2023);
        req.setRating(8.0);

        Movie saved = new Movie();
        saved.setId(77L);
        saved.setTitle("New");

        when(movieService.createNewMovie(any(MovieRequest.class))).thenReturn(Optional.of(saved));

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/movies/77"))
                .andExpect(jsonPath("$.id").value(77))
                .andExpect(jsonPath("$.title").value("New"));
    }

    @Test
    void createNewMovie_badRequest_whenServiceReturnsEmpty() throws Exception {
        MovieRequest req = new MovieRequest();
        req.setTitle("");

        when(movieService.createNewMovie(any(MovieRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateMovie_success_returns200() throws Exception {
        MovieRequest req = new MovieRequest();
        req.setTitle("Upd");
        req.setGenre("Action");
        req.setDuration(120);
        req.setReleaseYear(2024);
        req.setRating(9.0);

        Movie updated = new Movie();
        updated.setId(5L);
        updated.setTitle("Upd");

        when(movieService.updateMovie(5L, req)).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/movies/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Upd"));
    }

    @Test
    void updateMovie_badRequest_whenServiceReturnsEmpty() throws Exception {
        MovieRequest req = new MovieRequest();
        req.setTitle("DoesNotMatter");

        when(movieService.updateMovie(999L, req)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/movies/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMovie_noContent_whenDeleted() throws Exception {
        when(movieService.deleteMovie(10L)).thenReturn(true);

        mockMvc.perform(delete("/api/movies/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMovie_notFound_whenServiceReturnsFalse() throws Exception {
        when(movieService.deleteMovie(11L)).thenReturn(false);

        mockMvc.perform(delete("/api/movies/11"))
                .andExpect(status().isNotFound());
    }
}