package com.popcornpalace.moviebookingsystem.services;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final MovieRepository movieRepository;

    @Autowired
    public ShowtimeService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public Optional<Movie> getMovieById(Long id) {
        return Optional.ofNullable(movieRepository.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"))
        );
    }

    public Movie createNewMovie(Movie movie) {
        return movieRepository.createNewMovie(movie);
    }

    public Optional<Movie> updateMovie(Long id, Movie movieDetails) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setGenre(movieDetails.getGenre());
            movie.setDuration(movieDetails.getDuration());
            return movieRepository.save(movie);
        });
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteMovie(id);
            return true;
        }

        return false;
    }
}