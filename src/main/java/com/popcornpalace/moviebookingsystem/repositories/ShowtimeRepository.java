package com.popcornpalace.moviebookingsystem.repositories;

import com.popcornpalace.moviebookingsystem.models.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    default Optional<Showtime> getShowTimeById(Long id) {
        return findById(id);
    }

    default Showtime createNewShowTime(Showtime showtime) {
        return save(showtime);
    }

    default void deleteMovie(Long id) {
        deleteById(id);
    }
}