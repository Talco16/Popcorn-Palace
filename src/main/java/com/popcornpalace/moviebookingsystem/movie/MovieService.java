package com.popcornpalace.moviebookingsystem.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    // החזרת סרט לפי ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

//    // שמירת סרט חדש
//    public Movie saveMovie(Movie movie) {
//        return movieRepository.save(movie);
//    }
//
//    // עדכון סרט קיים
//    public Optional<Movie> updateMovie(Long id, Movie movieDetails) {
//        return movieRepository.findById(id).map(movie -> {
//            movie.setTitle(movieDetails.getTitle());
//            movie.setGenre(movieDetails.getGenre());
//            movie.setDuration(movieDetails.getDuration());
//            return movieRepository.save(movie);
//        });
//    }
//
//    // מחיקת סרט
//    public boolean deleteMovie(Long id) {
//        return movieRepository.findById(id).map(movie -> {
//            movieRepository.delete(movie);
//            return true;
//        }).orElse(false);
//    }
}