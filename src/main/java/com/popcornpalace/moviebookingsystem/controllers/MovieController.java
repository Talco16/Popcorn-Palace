package com.popcornpalace.moviebookingsystem.controllers;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.services.MovieService;
import com.popcornpalace.moviebookingsystem.util.rqeuests.MovieRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MovieController is responsible for managing Movie in the system.
 * It provides a RESTful API that supports standard CRUD operations.
 * Each operation is mapped to the conventional HTTP methods in REST:
 * POST to create new movie, Get for fetching all the movies,
 * PUT to update specific movie by id, and DELETE for delete one by id.
 * Created by Tal Cohen.
 */

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Todo: Need to add exceptions
    // GET Request to fetch all the Existing movies.
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // POST Request to create new movie
    @PostMapping
    public ResponseEntity<?> createNewMovie(@Valid @RequestBody MovieRequest movie) {
        try {
            return ResponseEntity.ok(movieService.createNewMovie(movie));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT Request to update movie by id
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieRequest movieDetails) {
        return movieService.updateMovie(id, movieDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // DELETE Request to remove movie by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}