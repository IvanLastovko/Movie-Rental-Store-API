package com.example.fujitsu;

import com.example.fujitsu.rental.models.Movies;
import com.example.fujitsu.rental.models.Rentals;
import com.example.fujitsu.rental.utils.ReadJSONFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReadJSONFileTest {

    @Test
    public void readMovies() {
        try {
            Movies movies = ReadJSONFile.readMovies("test-movies.json");
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
    public void readRentals() {
        try {
            Rentals rentals = ReadJSONFile.readRentals("test-rentals.json");
            assertNotNull(rentals);
            assertEquals(rentals.rentalList.get(0).ID, "fc471c82-33e9-43e6-bba4-24c04d489b07");
            assertEquals(rentals.rentalList.get(0).getDates().size(), 1);
            assertEquals(rentals.rentalList.get(0).getDates(), List.of("02.11.2022|4"));

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
        System.out.println(TimeUnit.MILLISECONDS.toDays(DateTime.now().getMillis())/7);
    }

    @Test
    public void checkOverlappingDates(){
        String date_duration1 = "02.11.2022|4";
        String date2 = "06.11.2022";
        String duration2 = "5";
        String date1 = date_duration1.split("\\|")[0];
        String duration1 = date_duration1.split("\\|")[1];

        DateTime time1_start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date1);
        DateTime time1_end = time1_start.plusMillis(Integer.parseInt(duration1)*86400000);
        DateTime time2_start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date2);
        DateTime time2_end = time2_start.plusMillis(Integer.parseInt(duration2)*86400000);
        System.out.println(time1_start + " " + time1_end + " " + time2_start + " " + time2_end);
        System.out.println(time1_end.minus(time1_start.getMillis()));
        System.out.println(time1_end.isBefore(time2_start));
    }

    @Test
    public void calculateWeeklyPrice(){
        DateTime currentDate = DateTime.now();
        DateTime releaseDate = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("18.11.2009");

        System.out.println((int) Math.floor(currentDate.minus(releaseDate.getMillis()).getMillis()/604800000.0));
    }
}
