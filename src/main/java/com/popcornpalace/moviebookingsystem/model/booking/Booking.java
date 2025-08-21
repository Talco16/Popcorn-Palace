package com.popcornpalace.moviebookingsystem.model.booking;

import com.popcornpalace.moviebookingsystem.model.showtime.Showtime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_showtime_seat", columnNames = {"showtime_id", "seat_number"})
        }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Represents the relationship with Showtime entity
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime; // Use the Showtime entity directly

    @Positive
    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @NotBlank
    @Column(nullable = false)
    private String userId;

    public long getShowtimeId() {
        return showtime.getId();
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getUserId() {
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


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Booking(){}

    public Booking( Showtime showtime , int seatNumber, String userId ){
        if (seatNumber < 1 ){
            throw new IllegalArgumentException("Seat number must be greater than 1");
        }

        this.showtime =showtime;
        this.seatNumber = seatNumber;
        this.userId = userId;
    }
}