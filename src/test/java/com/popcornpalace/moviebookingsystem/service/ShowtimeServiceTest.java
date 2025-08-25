package com.popcornpalace.moviebookingsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.repository.MovieRepository;
import com.popcornpalace.moviebookingsystem.repository.ShowtimeRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.ShowtimeRequest;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ShowtimeServiceTest {

  @Mock ShowtimeRepository showtimeRepository;
  @Mock MovieRepository movieRepository;

  @InjectMocks ShowtimeService showtimeService; // השם לפי המחלקה שלך

  @Test
  void getShowTimeById_found() {
    Showtime s = new Showtime();
    s.setId(10L);
    when(showtimeRepository.findById(10L)).thenReturn(Optional.of(s));

    Optional<Showtime> res = showtimeService.getShowTimeById(10L);

    assertThat(res).isPresent();
    assertThat(res.get().getId()).isEqualTo(10L);
  }

  @Test
  void createShowtime_success() {
    // given
    Movie m = new Movie();
    m.setId(1L);
    when(movieRepository.findById(1L)).thenReturn(Optional.of(m));

    // קובעים זמן בסיס קבוע כדי למנוע פלייקיות
    LocalDateTime base = LocalDateTime.of(2030, 1, 1, 10, 0);
    LocalDateTime start = base.plusHours(1); // 11:00
    LocalDateTime end = base.plusHours(3); // 13:00

    ShowtimeRequest req = new ShowtimeRequest();
    req.setMovieId(1L);
    req.setTheater("  Hall A  "); // עם רווחים – השירות מנרמל ל"Hall A"
    req.setStartTime(start);
    req.setEndTime(end);
    req.setPrice(25.0);

    // אין חפיפות
    when(showtimeRepository.findShowtimesByTimesAndTheater(
            any(LocalDateTime.class), any(LocalDateTime.class), eq("Hall A")))
        .thenReturn(Collections.emptyList());

    // החזרה של ה־entity עם מזהה אחרי save
    when(showtimeRepository.save(any(Showtime.class)))
        .thenAnswer(
            inv -> {
              Showtime s = inv.getArgument(0);
              s.setId(123L);
              return s;
            });

    // when
    Optional<Showtime> createdOpt = showtimeService.createNewShowtime(req);

    // then
    assertThat(createdOpt).isPresent();
    Showtime created = createdOpt.orElseThrow();

    assertThat(created.getId()).isEqualTo(123L);
    assertThat(created.getMovie().getId()).isEqualTo(1L);
    assertThat(created.getTheater()).isEqualTo("Hall A");
    assertThat(created.getStartTime()).isEqualTo(start);
    assertThat(created.getEndTime()).isEqualTo(end);
    assertThat(created.getPrice()).isEqualTo(25.0);

    // אופציונלי: וידוא אינטראקציות
    verify(movieRepository).findById(1L);
    verify(showtimeRepository).findShowtimesByTimesAndTheater(start, end, "Hall A");
    verify(showtimeRepository).save(any(Showtime.class));
  }

  @Test
  void createShowtime_movieNotFound_throws404() {
    when(movieRepository.findById(999L)).thenReturn(Optional.empty());
    ShowtimeRequest req = new ShowtimeRequest();
    req.setMovieId(999L);

    assertThatThrownBy(() -> showtimeService.createNewShowtime(req))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Movie not found");
  }
}
