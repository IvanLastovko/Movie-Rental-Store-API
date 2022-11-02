package com.example.fujitsu.rental;

import com.example.fujitsu.rental.models.*;
import com.example.fujitsu.rental.utils.AdminKeyScanner;
import com.example.fujitsu.rental.utils.OverlappingChecker;
import com.example.fujitsu.rental.utils.ReadJSONFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalService {


    public String getMovies(String category) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
        List<Movie> movieList = ReadJSONFile.readMovies("movies.json").movieList;
        if (category == null) {
            return objectMapper.writeValueAsString(movieList);
        }

        return objectMapper.writeValueAsString(movieList.stream().filter(mov -> Objects.equals(mov.metadata.getCategory(), category)).collect(Collectors.toList()));
    }

    public String findMovie(String id) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
            Movies movies = ReadJSONFile.readMovies("movies.json");
            Movie movie = movies.movieList.stream().filter(mov -> Objects.equals(mov.getID(), id)).toList().get(0);
            return objectMapper.writeValueAsString(movie);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie does not exist");
        }
    }

    public String addMovie(String adminKey, Movie newMovie) throws IOException {
        try {
            if (!AdminKeyScanner.validateKey(adminKey)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Admin key! You do not have permissions to add new movies!");
            }

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
            Movies movies = ReadJSONFile.readMovies("movies.json");
            newMovie.setID(UUID.randomUUID().toString());
            movies.movieList.add(newMovie);

            Resource resource = new ClassPathResource("movies.json");
            new ObjectMapper().writeValue(resource.getFile(), movies);

            return objectMapper.writeValueAsString(newMovie);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OOPS... Something is wrong with your request!", e);
        }
    }

    public String updateMovie(String adminKey, Movie movie, String id) {

        try {
            if (!AdminKeyScanner.validateKey(adminKey)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Admin key! You do not have permissions to add new movies!");
            }

            if (!id.equals(movie.ID)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDs in request URL and body do not match!!!");
            }


            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
            Movies movies = ReadJSONFile.readMovies("movies.json");
            boolean deleted = movies.movieList.removeIf(mov -> mov.getID().equals(id));
            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie does not exist!!!");
            }

            movies.movieList.add(movie);

            Resource resource = new ClassPathResource("movies.json");
            new ObjectMapper().writeValue(resource.getFile(), movies);

            return objectMapper.writeValueAsString(movie);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OOPS... Something is wrong with your request!", e);
        }

    }

    public String deleteMovie(String adminKey, String id) {

        try {
            if (!AdminKeyScanner.validateKey(adminKey)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Admin key! You do not have permissions to add new movies!");
            }

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
            Movies movies = ReadJSONFile.readMovies("movies.json");
            Movie movie = objectMapper.readValue(findMovie(id), Movie.class);

            boolean deleted = movies.movieList.removeIf(mov -> mov.getID().equals(id));
            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie does not exist!!!");
            }

            Resource resource = new ClassPathResource("movies.json");
            new ObjectMapper().writeValue(resource.getFile(), movies);

            return objectMapper.writeValueAsString(movie);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OOPS... Something is wrong with your request!", e);
        }
    }

    public boolean isAnyTrue(List<Boolean> array)
    {
        for(boolean b : array) if(b) return true;
        return false;
    }

    public double calculateRantCost(String duration, double price)
    {
        return Math.ceil(Float.parseFloat(duration)/7.0)*price;
    }

    public String bookMovie(String id, RentalSpecs rentalSpecs) throws IOException {

        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

            Rentals rentals = ReadJSONFile.readRentals("rentals.json");
            Rental movieRental = rentals.rentalList.stream().filter(rent -> Objects.equals(rent.getID(), id)).toList().get(0);
            Movie movie = objectMapper.readValue(findMovie(id), Movie.class);

            List<Boolean> overlappingDates = movieRental.dates.stream().map(date ->
                    OverlappingChecker.checkIfOverlap(date.split("\\|")[0],
                    date.split("\\|")[1],
                    rentalSpecs.getStartDate(),
                    rentalSpecs.getDuration())).toList();
            if(isAnyTrue(overlappingDates)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cannot rent for these dates. Movie is already booked!");
            }

            movieRental.dates.add(rentalSpecs.startDate + "|" + rentalSpecs.duration);
            rentals.rentalList.removeIf(rent -> Objects.equals(rent.getID(), id));
            rentals.rentalList.add(movieRental);
            Resource resource = new ClassPathResource("rentals.json");
            new ObjectMapper().writeValue(resource.getFile(), rentals);


            return "Your booking was successful! It will cost you " + calculateRantCost(rentalSpecs.duration, movie.metadata.getPrice());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OOPS... Something is wrong with your request!", e);
        }
    }
}
