package com.popcornpalace.moviebookingsystem.Repository;

import com.popcornpalace.moviebookingsystem.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Movie, Long> {
    boolean existsByShowtime_IdAndSeatNumber(Long showtimeId, String seatNumber);
}