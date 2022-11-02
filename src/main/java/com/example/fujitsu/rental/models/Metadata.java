package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Metadata {

    @JsonProperty(value = "category", required = true)
    public String category;
    @JsonProperty(value = "releaseDate", required = true)
    public String releaseDate;
    public List<String> actors;
    public String description;
    @JsonProperty(value = "price", required = true)
    public double price;

}
