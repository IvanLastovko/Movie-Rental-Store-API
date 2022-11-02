package com.example.fujitsu.rental;

import com.example.fujitsu.rental.models.Movie;
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
    public String addMovieIntoList(@RequestParam() String adminKey,
                                   @RequestBody Movie newMovie) throws IOException {
        return rentalService.addMovie(adminKey, newMovie);
    }
}
