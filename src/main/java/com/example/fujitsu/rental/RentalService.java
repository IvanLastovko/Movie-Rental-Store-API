package com.example.fujitsu.rental;

import com.example.fujitsu.rental.models.Movie;
import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.utils.AdminKeyScanner;
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
        List<Movie> movieList = ReadJSONFile.readJson("movies.json").movieList;
        if (category == null) {
            return objectMapper.writeValueAsString(movieList);
        }

        return objectMapper.writeValueAsString(movieList.stream().filter(mov -> Objects.equals(mov.metadata.getCategory(), category)).collect(Collectors.toList()));
    }

    public String findMovie(String id) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
            Movies movies = ReadJSONFile.readJson("movies.json");
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
            Movies movies = ReadJSONFile.readJson("movies.json");
            newMovie.setID(UUID.randomUUID().toString());
            movies.movieList.add(newMovie);

            Resource resource = new ClassPathResource("movies.json");
            new ObjectMapper().writeValue(resource.getFile(), movies);

            return objectMapper.writeValueAsString(newMovie);

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OOPS... Something is wrong with your request!", e);
        }
    }
}
