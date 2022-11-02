package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Movie {

    @JsonProperty("id")
    public String ID;
    public String name;
    public Metadata metadata;

}
