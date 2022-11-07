package com.example.fujitsu.rental.utils;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.models.Rental;
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

        return objectMapper.readValue(resource.getFile(), Movies.class);
    }

    public static Rentals readRentals(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

        Rentals rentals = new Rentals();
        if(resource.getFile().length() > 0){
            rentals = objectMapper.readValue(resource.getFile(), Rentals.class);
        } else{
            rentals.setRentalList(new ArrayList<>());
        }

        return rentals;
    }
}
