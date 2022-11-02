package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Movies {
    @JsonProperty("movies")
    public List<Movie> movieList;
}
