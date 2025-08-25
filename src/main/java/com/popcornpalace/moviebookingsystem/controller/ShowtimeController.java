package com.popcornpalace.moviebookingsystem.controller;

import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.service.ShowtimeService;
import com.popcornpalace.moviebookingsystem.util.exception.ErrorResponse;
import com.popcornpalace.moviebookingsystem.util.reqeuest.ShowtimeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ShowtimeController is responsible for managing Showtime in the system. It provides a RESTful API
 * that supports standard CRUD operations. Each operation is mapped to the conventional HTTP methods
 * in REST: POST to create new showtime, Get by id for fetching specific showtime, PUT to update
 * specific showtime by id, and DELETE for delete one by id. Created by Tal Cohen.
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
  @Operation(summary = "Fetch showtime by id", description = "Fetch showtime by id")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Fetch showtime by id",
        content = @Content(schema = @Schema(implementation = Showtime.class))),
  })
  @GetMapping("/{id}")
  public ResponseEntity<Showtime> getShowTimeById(@PathVariable Long id) {
    return ResponseEntity.of(showtimeService.getShowTimeById(id));
  }

  // POST Request to add showtime.
  @Operation(summary = "Create new showtime", description = "Create new showtime")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "created",
        content = @Content(schema = @Schema(implementation = Showtime.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Bad request",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<?> createShowtime(@Valid @RequestBody ShowtimeRequest showtime) {
    return showtimeService
        .createNewShowtime(showtime)
        .map(
            saved ->
                ResponseEntity.created(URI.create("/api/showtimes/" + saved.getId())).body(saved))
        .orElse(ResponseEntity.badRequest().build());
  }

  // PUT Request to update showtime by id.
  @Operation(summary = "Update showtime by id", description = "Update showtime by id")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "created",
        content = @Content(schema = @Schema(implementation = Showtime.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Bad request",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateShowTime(
      @PathVariable Long id, @RequestBody ShowtimeRequest showtime) {
    return showtimeService
        .updateShowtime(id, showtime)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // DELETE Request to update showtime by id.
  @Operation(summary = "Delete showtime by id", description = "Delete showtime by id")
  @ApiResponses({
    @ApiResponse(
        responseCode = "204",
        description = "movie deleted",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Not Found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteShowTime(@PathVariable Long id) {
    if (showtimeService.deleteShowtime(id)) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
