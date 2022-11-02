package com.example.fujitsu;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.utils.ReadJSONFile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReadJSONFileTest {

    @Test
    public void readJsonFile() {
        System.out.println(UUID.randomUUID());
        try {
            Movies movies = ReadJSONFile.readJson("test-movies.json");
            assertNotNull(movies);
            assertEquals(movies.movieList.get(0).ID, "123456789");
            assertEquals(movies.movieList.get(0).name, "Avatar");
            assertNotNull(movies.movieList.get(0).metadata);
            assertEquals(movies.movieList.get(0).metadata.category, "Adventure");
            assertEquals(movies.movieList.get(0).metadata.releaseDate.toDateTime().toString(), "2009-11-18T00:00:00.000Z");
            assertNotNull(movies.movieList.get(0).metadata.actors);
            assertEquals(movies.movieList.get(0).metadata.actors.size(), 3);
            assertEquals(movies.movieList.get(0).metadata.actors.get(0), "Zoë Yadira Saldaña-Perego");
            assertEquals(movies.movieList.get(0).metadata.description, "some description");
            assertEquals(movies.movieList.get(0).metadata.price, 1.99);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
