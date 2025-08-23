package com.popcornpalace.moviebookingsystem.Service;

import com.popcornpalace.moviebookingsystem.Model.Movie;
import com.popcornpalace.moviebookingsystem.Repository.MovieRepository;
import com.popcornpalace.moviebookingsystem.Util.Reqeuest.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public Optional<Movie> getMovieById(Long id) {
        return Optional.ofNullable(movieRepository.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"))
        );
    }

    public Movie createNewMovie(MovieRequest movie) {
        return movieRepository.createNewMovie(movie);
    }

    public Optional<Movie> updateMovie(Long id, MovieRequest movie) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setGenre(movieDetails.getGenre());
            movie.setDuration(movieDetails.getDuration());
            return movieRepository.save(movie);
        });
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteMovie(id);
            return true;
        }

        return false;
    }
}

//@Service
//@Transactional(readOnly = true)
//public class MovieService {
//
//    private final MovieRepository movieRepo;
//
//    public MovieService(MovieRepository movieRepo) {
//        this.movieRepo = movieRepo;
//    }
//
//    // GET /api/movies
//    public List<Movie> getAllMovies() {
//        return movieRepo.findAll();
//    }
//
//    // POST /api/movies
//    @Transactional
//    public Optional<Movie> createNewMovie(@Valid MovieRequest req) {
//        // אפשר לבצע נרמולים/בדיקות עסקיות נוספות מעבר ל-Bean Validation
//        Movie m = new Movie();
//        m.setTitle(req.getTitle().trim());
//        m.setGenre(req.getGenre().trim());
//        m.setDuration(req.getDuration());
//        m.setReleaseYear(req.getReleaseYear());
//        m.setRating(req.getRating());
//
//        Movie saved = movieRepo.save(m);
//        return Optional.of(saved);
//    }
//
//    // PUT /api/movies/{id}
//    @Transactional
//    public Optional<Movie> updateMovie(Long id, @Valid MovieRequest req) {
//        return movieRepo.findById(id).map(existing -> {
//            existing.setTitle(req.getTitle().trim());
//            existing.setGenre(req.getGenre().trim());
//            existing.setDuration(req.getDuration());
//            existing.setReleaseYear(req.getReleaseYear());
//            existing.setRating(req.getRating());
//            // אין צורך ב-save מפורש; JPA יעדכן בסוף הטרנזקציה, אבל זה גם בסדר:
//            return existing;
//        });
//    }
//
//    // מחיקה — מחזיר true אם נמחק, false אם לא נמצא
//    @Transactional
//    public boolean deleteMovie(Long id) {
//        if (!movieRepo.existsById(id)) return false;
//        movieRepo.deleteById(id);
//        return true;
//    }
//
//    // אופציונלי — לשליפה נקודתית בקונטרולר עתידי
//    public Movie getByIdOrThrow(Long id) {
//        return movieRepo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + id));
//    }
//}