package com.popcornpalace.moviebookingsystem.repositories;

import com.popcornpalace.moviebookingsystem.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Movie, Long> {
    default List<Movie> getAllMovies() {
        return findAll();
    }

    default Optional<Movie> getMovieById(Long id) {
        return findById(id);
    }

    default Movie createNewMovie(Movie movie) {
        return save(movie);
    }

    default void deleteMovie(Long id) {
        deleteById(id);
    }
}