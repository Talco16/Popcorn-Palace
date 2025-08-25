package com.popcornpalace.moviebookingsystem.repository;

import com.popcornpalace.moviebookingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  boolean existsByShowtime_IdAndSeatNumber(Long showtimeId, int seatNumber);
}
