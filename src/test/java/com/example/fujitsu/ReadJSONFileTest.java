package com.example.fujitsu;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.models.RentalSpecs;
import com.example.fujitsu.rental.models.Rentals;
import com.example.fujitsu.rental.utils.ReadJSONFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReadJSONFileTest {

    @Test
    public void readMovies() {
        try {
            Movies movies = ReadJSONFile.readMovies("test-movies.json");
            assertNotNull(movies);
            assertEquals(movies.getMovieList().get(0).getID(), "123456789");
            assertEquals(movies.getMovieList().get(0).getName(), "Avatar");
            assertNotNull(movies.getMovieList().get(0).getMetadata());
            assertEquals(movies.getMovieList().get(0).getMetadata().getCategory(), "Adventure");
            assertEquals(movies.getMovieList().get(0).getMetadata().getReleaseDate(), "18.11.2009");
            assertNotNull(movies.getMovieList().get(0).getMetadata().getActors());
            assertEquals(movies.getMovieList().get(0).getMetadata().getActors().size(), 3);
            assertEquals(movies.getMovieList().get(0).getMetadata().getActors().get(0), "Zoë Yadira Saldaña-Perego");
            assertEquals(movies.getMovieList().get(0).getMetadata().getDescription(), "some description");
            assertEquals(movies.getMovieList().get(0).getMetadata().getPrice(), 1.99);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readRentals() {
        try {
            Rentals rentals = ReadJSONFile.readRentals("test-rentals.json");
            assertNotNull(rentals);
            assertEquals(rentals.getRentalList().get(0).getID(), "fc471c82-33e9-43e6-bba4-24c04d489b07");
            assertEquals(rentals.getRentalList().get(0).getDates().size(), 1);
            assertEquals(rentals.getRentalList().get(0).getDates(), List.of("02.11.2022|4"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readRentalSpecs() {
        try {

            Resource resource = new ClassPathResource("test-rental-specs.json");
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

            RentalSpecs rentalSpecs = objectMapper.readValue(resource.getFile(), RentalSpecs.class);

            assertNotNull(rentalSpecs);
            assertEquals(rentalSpecs.getDuration(), "4");
            assertEquals(rentalSpecs.getStartDate(), "02.11.2022");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
