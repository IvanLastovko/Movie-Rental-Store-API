package com.example.fujitsu.rental;

import com.example.fujitsu.rental.models.Movie;
import com.example.fujitsu.rental.models.RentalSpecs;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class RentalController {

    @Resource
    RentalService rentalService;

    @GetMapping(value = "/api/v1/movies", produces = "application/json")
    public String returnListOfMoviesByGenre(@RequestParam(required = false) String category) throws IOException {
        return rentalService.getMovies(category);
    }

    @GetMapping(value = "/api/v1/movies/{id}", produces = "application/json")
    public String findMoviesById(@PathVariable String id) throws IOException {
        return rentalService.findMovie(id);
    }

    @PostMapping(value = "/api/v1/movies", produces = "application/json")
    public String addMovieIntoList(@RequestParam() String adminKey, @RequestBody Movie newMovie) throws IOException {
        return rentalService.addMovie(adminKey, newMovie);
    }

    @PutMapping(value = "/api/v1/movies/{id}", produces = "application/json")
    public String updateMovie(@RequestParam() String adminKey, @PathVariable String id, @RequestBody Movie movie) {
        return rentalService.updateMovie(adminKey, movie, id);
    }

    @DeleteMapping(value = "/api/v1/movies/{id}", produces = "application/json")
    public String deleteMovie(@RequestParam() String adminKey, @PathVariable String id) {
        return rentalService.deleteMovie(adminKey, id);
    }

    @PostMapping(value = "/api/v1/rental/{id}", produces = "application/json")
    public String bookMovie(@PathVariable String id, @RequestBody RentalSpecs rentalSpecs) {
        return rentalService.bookMovie(id, rentalSpecs);
    }

    @GetMapping(value = "/api/v1/rentals", produces = "application/json")
    public String returnListOfMoviesByGenre() {
        return rentalService.getRentals();
    }

}
