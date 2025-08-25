package com.popcornpalace.moviebookingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "bookings",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_showtime_seat",
          columnNames = {"showtime_id", "seat_number"})
    },
    indexes = {@Index(name = "idx_booking_showtime", columnList = "showtime_id")})
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "showtime_id", nullable = false)
  private Showtime showtime;

  @Positive
  @Column(name = "seat_number", nullable = false)
  private int seatNumber;

  @NotNull
  @JdbcTypeCode(SqlTypes.UUID) // מבטיח מיפוי UUID אמיתי בפוסטגרס
  @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
  private UUID userId;

  public Booking() {}

  public Booking(Showtime showtime, int seatNumber, UUID userId) {
    this.showtime = showtime;
    this.seatNumber = seatNumber;
    this.userId = userId;
  }

  public long getShowtimeId() {
    return showtime.getId();
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setShowtime(Showtime showtime) {
    this.showtime = showtime;
  }

  public UUID getUserId() {
    return userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
