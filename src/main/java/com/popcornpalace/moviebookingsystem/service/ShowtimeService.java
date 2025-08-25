package com.popcornpalace.moviebookingsystem.service;

import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.repository.MovieRepository;
import com.popcornpalace.moviebookingsystem.repository.ShowtimeRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.ShowtimeRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShowtimeService {

  private final ShowtimeRepository showtimeRepository;
  private final MovieRepository movieRepository;

  public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
    this.showtimeRepository = showtimeRepository;
    this.movieRepository = movieRepository;
  }

  public Optional<Showtime> getShowTimeById(Long id) {
    return showtimeRepository.findById(id);
  }

  @Transactional
  public Optional<Showtime> createNewShowtime(@Valid ShowtimeRequest req) {
    Movie movie =
        movieRepository
            .findById(req.getMovieId())
            .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + req.getMovieId()));

    String theater = normalize(req.getTheater());
    LocalDateTime start = requireTimes(req.getStartTime(), "startTime");
    LocalDateTime end = requireTimes(req.getEndTime(), "endTime");
    requireEndAfterStart(start, end);
    requirePrice(req.getPrice());

    List<Showtime> overlaps =
        showtimeRepository.findShowtimesByTimesAndTheater(start, end, theater);

    if (!overlaps.isEmpty()) {
      throw new IllegalStateException("Showtime overlaps in theater: " + theater);
    }

    Showtime newShowTime = new Showtime();
    newShowTime.setMovie(movie);
    newShowTime.setTheater(theater);
    newShowTime.setStartTime(start);
    newShowTime.setEndTime(end);
    newShowTime.setPrice(req.getPrice());

    return Optional.of(showtimeRepository.save(newShowTime));
  }

  @Transactional
  public Optional<Showtime> updateShowtime(Long id, @Valid ShowtimeRequest updatedShowtime) {
    return showtimeRepository
        .findById(id)
        .map(
            showtime -> {
              if (!Objects.equals(showtime.getMovie().getId(), updatedShowtime.getMovieId())) {
                Movie movie =
                    movieRepository
                        .findById(updatedShowtime.getMovieId())
                        .orElseThrow(
                            () ->
                                new EntityNotFoundException(
                                    "Movie not found: " + updatedShowtime.getMovieId()));
                showtime.setMovie(movie);
              }

              String theater = normalize(updatedShowtime.getTheater());
              LocalDateTime start = requireTimes(updatedShowtime.getStartTime(), "startTime");
              LocalDateTime end = requireTimes(updatedShowtime.getEndTime(), "endTime");
              requireEndAfterStart(start, end);
              requirePrice(updatedShowtime.getPrice());

              List<Showtime> overlaps =
                  showtimeRepository.findShowtimesByTimesAndTheater(start, end, theater);
              boolean hasOtherOverlap =
                  overlaps.stream().anyMatch(s -> !s.getId().equals(showtime.getId()));

              if (hasOtherOverlap) {
                throw new IllegalStateException("Updated showtime overlaps in theater: " + theater);
              }

              showtime.setTheater(theater);
              showtime.setStartTime(start);
              showtime.setEndTime(end);
              showtime.setPrice(updatedShowtime.getPrice());

              return showtime;
            });
  }

  @Transactional
  public boolean deleteShowtime(Long id) {
    if (!showtimeRepository.existsById(id)) return false;
    showtimeRepository.deleteById(id);
    return true;
  }

  private static String normalize(String theater) {
    if (theater == null || theater.isBlank()) {
      throw new IllegalArgumentException("Theater is required");
    }
    return theater.trim();
  }

  private static LocalDateTime requireTimes(LocalDateTime time, String field) {
    if (time == null) throw new IllegalArgumentException(field + " is required");
    return time;
  }

  private static void requireEndAfterStart(LocalDateTime start, LocalDateTime end) {
    if (!end.isAfter(start)) {
      throw new IllegalArgumentException("endTime must be after startTime");
    }
  }

  private static void requirePrice(double price) {
    if (price < 1.0) {
      throw new IllegalArgumentException("Price must be >= 1.0");
    }
  }
}
