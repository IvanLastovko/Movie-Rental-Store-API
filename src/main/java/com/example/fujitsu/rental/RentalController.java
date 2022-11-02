package com.example.fujitsu.rental;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
