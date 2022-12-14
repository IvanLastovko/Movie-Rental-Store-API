package com.example.fujitsu.rental.utils;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.models.Rentals;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;

public class ReadJSONFile {

    public static Movies readMovies(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

        Movies movies = new Movies();
        if(resource.getFile().length() > 0){
            movies = objectMapper.readValue(resource.getFile(), Movies.class);
        } else {
            movies.setMovieList(new ArrayList<>());
        }

        return movies;
    }

    public static Rentals readRentals(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

        Rentals rentals = new Rentals();
        if (resource.getFile().length() > 0) {
            rentals = objectMapper.readValue(resource.getFile(), Rentals.class);
        } else {
            rentals.setRentalList(new ArrayList<>());
        }

        return rentals;
    }
}
