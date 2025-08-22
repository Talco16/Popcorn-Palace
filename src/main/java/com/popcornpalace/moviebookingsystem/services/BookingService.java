package com.popcornpalace.moviebookingsystem.services;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    private final MovieRepository movieRepository;

    @Autowired
    public BookingService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> bookTicket(Long id, Movie movieDetails) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setGenre(movieDetails.getGenre());
            movie.setDuration(movieDetails.getDuration());
            return movieRepository.save(movie);
        });
    }
}