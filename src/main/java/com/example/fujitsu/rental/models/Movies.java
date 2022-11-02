package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Movies {
    @JsonProperty("movies")
    public List<Movie> movieList;
}
