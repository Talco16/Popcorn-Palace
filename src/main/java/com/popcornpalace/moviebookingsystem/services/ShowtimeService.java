package com.popcornpalace.moviebookingsystem.services;

import com.popcornpalace.moviebookingsystem.models.Movie;
import com.popcornpalace.moviebookingsystem.models.Showtime;
import com.popcornpalace.moviebookingsystem.repositories.ShowtimeRepository;
import com.popcornpalace.moviebookingsystem.util.rqeuests.ShowtimeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public Optional<Showtime> getShowTimeById(Long id) {
        return Optional.ofNullable(showtimeRepository.getShowTimeById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"))
        );
    }

    public Showtime createNewShowTime(Showtime showtime) {
        return showtimeRepository.createNewShowTime(showtime);
    }

    public Showtime updateShowTime(Long id, ShowtimeRequest showtime) {
        return this.getShowTimeById(id).map(showtime -> {
            //TODO: Fix this logic!
//            showtime.setTitle(movieDetails.getTitle());
//            showtime.setGenre(movieDetails.getGenre());
//            showtime.setDuration(movieDetails.getDuration());
            return showtimeRepository.save(movie);
        });
    }

    public boolean deleteMovie(Long id) {
        if (this.getShowTimeById(id).isPresent()) {
            showtimeRepository.deleteMovie(id);
            return true;
        }

        return false;
    }
}