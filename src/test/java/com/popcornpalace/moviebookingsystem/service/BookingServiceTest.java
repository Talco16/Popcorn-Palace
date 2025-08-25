package com.popcornpalace.moviebookingsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.popcornpalace.moviebookingsystem.model.Booking;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.repository.BookingRepository;
import com.popcornpalace.moviebookingsystem.repository.ShowtimeRepository;
import com.popcornpalace.moviebookingsystem.util.reqeuest.BookingRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

  @Mock BookingRepository bookingRepository;

  @Mock ShowtimeRepository showtimeRepository;

  @InjectMocks BookingService bookingService;

  @Test
  void bookTicket_success_savesAndReturnsBooking() {
    BookingRequest req = new BookingRequest();
    req.setShowtimeId(7L);
    req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
    req.setSeatNumber(11);

    Showtime st = new Showtime();
    st.setId(7L);
    when(showtimeRepository.findByIdWithLock(7L)).thenReturn(Optional.of(st));
    when(bookingRepository.existsByShowtime_IdAndSeatNumber(7L, 11)).thenReturn(false);
    when(bookingRepository.saveAndFlush(any(Booking.class)))
        .thenAnswer(
            inv -> {
              Booking b = inv.getArgument(0);
              b.setId(555L);
              return b;
            });

    Booking saved = bookingService.bookTicket(req);

    assertThat(saved.getId()).isEqualTo(555L);
    assertThat(saved.getShowtimeId()).isEqualTo(7L);
    assertThat(saved.getSeatNumber()).isEqualTo(11);
    assertThat(saved.getUserId()).isEqualTo(UUID.fromString(req.getUserId()));

    verify(showtimeRepository).findByIdWithLock(7L);
    verify(bookingRepository).existsByShowtime_IdAndSeatNumber(7L, 11);
    verify(bookingRepository).saveAndFlush(any(Booking.class));
  }

  @Test
  void bookTicket_showtimeNotFound_throwsEntityNotFound() {
    BookingRequest req = new BookingRequest();
    req.setShowtimeId(99L);
    req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
    req.setSeatNumber(1);

    when(showtimeRepository.findByIdWithLock(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> bookingService.bookTicket(req))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Showtime not found: 99");
  }

  @Test
  void bookTicket_seatAlreadyBooked_precheck_throwsIllegalState() {
    BookingRequest req = new BookingRequest();
    req.setShowtimeId(7L);
    req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
    req.setSeatNumber(11);

    Showtime st = new Showtime();
    st.setId(7L);
    when(showtimeRepository.findByIdWithLock(7L)).thenReturn(Optional.of(st));
    when(bookingRepository.existsByShowtime_IdAndSeatNumber(7L, 11)).thenReturn(true);

    assertThatThrownBy(() -> bookingService.bookTicket(req))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Seat already booked: 11");

    verify(bookingRepository, never()).saveAndFlush(any());
  }

  @Test
  void bookTicket_uniqueConstraintRace_caughtAndWrappedAsIllegalState() {
    BookingRequest req = new BookingRequest();
    req.setShowtimeId(7L);
    req.setUserId("a3f5c6d7-1111-2222-3333-444444444444");
    req.setSeatNumber(11);

    Showtime st = new Showtime();
    st.setId(7L);
    when(showtimeRepository.findByIdWithLock(7L)).thenReturn(Optional.of(st));
    when(bookingRepository.existsByShowtime_IdAndSeatNumber(7L, 11)).thenReturn(false);
    when(bookingRepository.saveAndFlush(any(Booking.class)))
        .thenThrow(new DataIntegrityViolationException("duplicate key"));

    assertThatThrownBy(() -> bookingService.bookTicket(req))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Seat already booked: 11");
  }
}
