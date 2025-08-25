package com.popcornpalace.moviebookingsystem.service;

import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.repository.MovieRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.MovieRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  @Transactional
  public Optional<Movie> createNewMovie(@Valid MovieRequest movie) {
    Movie newMovie = new Movie();
    newMovie.setTitle(movie.getTitle());
    newMovie.setGenre(movie.getGenre());
    newMovie.setDuration(movie.getDuration());
    newMovie.setReleaseYear(movie.getReleaseYear());
    newMovie.setRating(movie.getRating());

    return Optional.of(movieRepository.save(newMovie));
  }

  @Transactional
  public Optional<Movie> updateMovie(Long id, @Valid MovieRequest updatedMovie) {
    return movieRepository
        .findById(id)
        .map(
            movie -> {
              movie.setTitle(updatedMovie.getTitle());
              movie.setGenre(updatedMovie.getGenre());
              movie.setDuration(updatedMovie.getDuration());
              movie.setReleaseYear(updatedMovie.getReleaseYear());
              movie.setRating(updatedMovie.getRating());

              return movie;
            });
  }

  @Transactional
  public boolean deleteMovie(Long id) {
    if (!movieRepository.existsById(id)) return false;

    movieRepository.deleteById(id);
    return true;
  }
}
