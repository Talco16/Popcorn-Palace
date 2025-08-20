package com.popcornpalace.moviebookingsystem.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    default List<Movie> getAllMovies() {
        return findAll();
    }
}