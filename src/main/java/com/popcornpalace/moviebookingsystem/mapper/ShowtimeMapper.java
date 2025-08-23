package com.popcornpalace.moviebookingsystem.mapper;

import com.popcornpalace.moviebookingsystem.model.Movie;
import com.popcornpalace.moviebookingsystem.model.Showtime;
import com.popcornpalace.moviebookingsystem.util.response.MovieSummary;
import com.popcornpalace.moviebookingsystem.util.response.ShowtimeResponse;
import org.springframework.stereotype.Component;

@Component
public class ShowtimeMapper {
    public ShowtimeResponse toDto(Showtime showtime) {
        Movie m = showtime.getMovie();
        var ms = new MovieSummary(m.getId(), m.getTitle(), m.getGenre(), m.getReleaseYear(), m.getRating());
        return new ShowtimeResponse(showtime.getId(), showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime(), showtime.getPrice(), ms);
    }
}