package com.popcornpalace.moviebookingsystem.controllers;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.models.Showtime;
import com.popcornpalace.moviebookingsystem.services.ShowtimeService;
import com.popcornpalace.moviebookingsystem.util.rqeuests.ShowtimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ShowtimeController is responsible for managing Showtime in the system.
 * It provides a RESTful API that supports standard CRUD operations.
 * Each operation is mapped to the conventional HTTP methods in REST:
 * POST to create new showtime, Get by id for fetching specific showtime,
 * PUT to update specific showtime by id, and DELETE for delete one by id.
 * Created by Tal Cohen.
 */

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    // GET Request to fetch specific showtime by id.
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowTimeById(@PathVariable Long id) {
        return showtimeService.getShowTimeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST Request to add showtime.
    @PostMapping
    public ResponseEntity<?> createNewShowTime(@RequestBody ShowtimeRequest movie) {
        try {
            return ResponseEntity.ok(showtimeService.createNewShowTime(movie));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return showtimeService.createNewShowTime(movie);
    }

    // PUT Request to update showtime by id.
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShowTime(@PathVariable Long id, @RequestBody ShowtimeRequest showtimeDetails) {
        return showtimeService.updateShowTime(id, showtimeDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE Request to update showtime by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (showtimeService.deleteMovie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}