package com.popcornpalace.moviebookingsystem.controller;

import com.popcornpalace.moviebookingsystem.model.Movie;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    // GET Request to fetch all the Existing movies.
    @Operation(summary = "Fetch movies", description = "Fetch all existing movies in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok",
                    content = @Content(schema = @Schema(implementation = Movie.class))),
    })
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // POST Request to create new movie
    @Operation(summary = "Create movie", description = "Create new movie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created",
                    content = @Content(schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> createNewMovie(@Valid @RequestBody MovieRequest movie) {
        return movieService.createNewMovie(movie)
                .map(saved -> ResponseEntity
                        .created(URI.create("/api/movies/" + saved.getId()))
                        .body(saved))
                .orElse(ResponseEntity.badRequest().build());
    }

    // PUT Request to update movie by id
    @Operation(summary = "Update movie", description = "Update existing movie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created",
                    content = @Content(schema = @Schema(implementation = Movie.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieRequest movieDetails) {
        return movieService.updateMovie(id, movieDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // DELETE Request to remove movie by id.
    @Operation(summary = "Delete movie", description = "Delete movie by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "movie deleted",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}