package com.example.fujitsu;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.utils.ReadJSONFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReadJSONFileTest {

    @Test
    public void readJsonFile() {
        try {
            Movies movies = ReadJSONFile.readJson("test-movies.json");
            assertNotNull(movies);
            assertEquals(movies.movieList.get(0).ID, "123456789");
            assertEquals(movies.movieList.get(0).name, "Avatar");
            assertNotNull(movies.movieList.get(0).metadata);
            assertEquals(movies.movieList.get(0).metadata.category, "Adventure");
            assertEquals(movies.movieList.get(0).metadata.releaseDate, "18.11.2009");
            assertNotNull(movies.movieList.get(0).metadata.actors);
            assertEquals(movies.movieList.get(0).metadata.actors.size(), 3);
            assertEquals(movies.movieList.get(0).metadata.actors.get(0), "Zoë Yadira Saldaña-Perego");
            assertEquals(movies.movieList.get(0).metadata.description, "some description");
            assertEquals(movies.movieList.get(0).metadata.price, 1.99);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void parsingTime(){
        DateTime time1 = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("08.11.2021");
        DateTime time2 = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("06.12.2019");
        System.out.println(time1.getMillis());
        System.out.println(time2.getMillis());
        System.out.println(time1.plus(time1.minus(time2.getMillis()).getMillis()));
    }
}
