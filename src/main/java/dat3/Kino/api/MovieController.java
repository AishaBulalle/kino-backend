package dat3.Kino.api;

import dat3.Kino.dto.MovieDTO;

import dat3.Kino.service.MovieService;
import dat3.Kino.repository.MovieRespository;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieRespository movieRepository;


    @Autowired
    public MovieController(MovieService movieService, MovieRespository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String id) {
        Optional<MovieDTO> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO createdMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{imdbID}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String imdbID, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(movieDTO, imdbID);

        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{imdbID}")
    public ResponseEntity<MovieDTO> deleteMovie(@PathVariable String imdbID) {
        MovieDTO deletedMovie = movieService.deleteMovieByImdbID(imdbID);
        if (deletedMovie != null) {
            return ResponseEntity.ok(deletedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}