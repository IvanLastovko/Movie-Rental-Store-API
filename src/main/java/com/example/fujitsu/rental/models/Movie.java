package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Movie {

    @JsonProperty(value = "id")
    public String ID;
    @JsonProperty(value = "name", required = true)
    public String name;
    @JsonProperty(value = "metadata", required = true)
    public Metadata metadata;

}
