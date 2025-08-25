package com.popcornpalace.moviebookingsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.repository.MovieRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.MovieRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

  @Mock MovieRepository movieRepository;

  @InjectMocks MovieService movieService;

  @Test
  void getAllMovies_returnsList() {
    when(movieRepository.findAll()).thenReturn(List.of(new Movie(), new Movie()));

    List<Movie> result = movieService.getAllMovies();

    assertThat(result).hasSize(2);
    verify(movieRepository).findAll();
  }

  @Test
  void createNewMovie_persistsAndReturnsMovie() {
    MovieRequest req = new MovieRequest();
    req.setTitle("Inception");
    req.setGenre("Sci-Fi");
    req.setDuration(148);
    req.setReleaseYear(2010);
    req.setRating(8.8);

    when(movieRepository.save(any(Movie.class)))
        .thenAnswer(
            inv -> {
              Movie m = inv.getArgument(0);
              m.setId(100L);
              return m;
            });

    Optional<Movie> saved = movieService.createNewMovie(req);

    assertThat(saved).isPresent();
    assertThat(saved.get().getId()).isEqualTo(100L);
    assertThat(saved.get().getTitle()).isEqualTo("Inception");
    verify(movieRepository).save(any(Movie.class));
  }

  @Test
  void updateMovie_whenExists_updatesAndReturns() {
    Movie existing = new Movie();
    existing.setId(5L);
    existing.setTitle("Old");
    existing.setGenre("Drama");
    existing.setDuration(100);
    existing.setReleaseYear(2000);
    existing.setRating(6.0);

    when(movieRepository.findById(5L)).thenReturn(Optional.of(existing));

    MovieRequest req = new MovieRequest();
    req.setTitle("New");
    req.setGenre("Action");
    req.setDuration(120);
    req.setReleaseYear(2024);
    req.setRating(9.1);

    Optional<Movie> updated = movieService.updateMovie(5L, req);

    assertThat(updated).isPresent();
    Movie m = updated.get();
    assertThat(m.getTitle()).isEqualTo("New");
    assertThat(m.getGenre()).isEqualTo("Action");
    assertThat(m.getDuration()).isEqualTo(120);
    assertThat(m.getReleaseYear()).isEqualTo(2024);
    assertThat(m.getRating()).isEqualTo(9.1);
    verify(movieRepository).findById(5L);
    verifyNoMoreInteractions(movieRepository);
  }

  @Test
  void updateMovie_whenNotExists_returnsEmpty() {
    when(movieRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<Movie> updated = movieService.updateMovie(999L, new MovieRequest());

    assertThat(updated).isEmpty();
    verify(movieRepository).findById(999L);
  }

  @Test
  void deleteMovie_whenExists_deletesAndReturnsTrue() {
    when(movieRepository.existsById(10L)).thenReturn(true);

    boolean deleted = movieService.deleteMovie(10L);

    assertThat(deleted).isTrue();
    verify(movieRepository).existsById(10L);
    verify(movieRepository).deleteById(10L);
  }

  @Test
  void deleteMovie_whenNotExists_returnsFalse() {
    when(movieRepository.existsById(11L)).thenReturn(false);

    boolean deleted = movieService.deleteMovie(11L);

    assertThat(deleted).isFalse();
    verify(movieRepository).existsById(11L);
    verify(movieRepository, never()).deleteById(anyLong());
  }
}
