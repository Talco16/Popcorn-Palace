package com.popcornpalace.moviebookingsystem.repository;

import com.popcornpalace.moviebookingsystem.model.Showtime;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
  @Query(
      "SELECT s FROM Showtime s WHERE "
          + "(s.startTime < :endTime AND s.endTime > :startTime) AND "
          + "s.theater = :theater")
  List<Showtime> findShowtimesByTimesAndTheater(
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime,
      @Param("theater") String theater);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT s FROM Showtime s WHERE s.id = :id")
  Optional<Showtime> findByIdWithLock(@Param("id") Long id);

  @EntityGraph(attributePaths = "movie")
  @Query("select s from Showtime s where s.id = :id")
  Optional<Showtime> findWithMovieById(@Param("id") Long id);
}
