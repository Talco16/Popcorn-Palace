package com.popcornpalace.moviebookingsystem.controller;

import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.service.ShowtimeService;
import com.popcornpalace.moviebookingsystem.util.reqeuest.ShowtimeRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Showtime> one(@PathVariable Long id) {
        return ResponseEntity.of(showtimeService.getShowTimeById(id));
    }

    // POST Request to add showtime.
    @PostMapping
    public ResponseEntity<?> createShowtime(@Valid @RequestBody ShowtimeRequest showtime) {
        return showtimeService.createNewShowtime(showtime)
                .map(saved -> ResponseEntity
                        .created(URI.create("/api/showtimes/" + saved.getId()))
                        .body(saved))
                .orElse(ResponseEntity.badRequest().build());
    }

    // PUT Request to update showtime by id.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateShowTime(@PathVariable Long id, @RequestBody ShowtimeRequest showtime) {
        return showtimeService.updateShowtime(id, showtime)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE Request to update showtime by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowTime(@PathVariable Long id) {
        if (showtimeService.deleteShowtime(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}