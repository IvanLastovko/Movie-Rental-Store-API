package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Rental {
    @JsonProperty(value = "id", required = true)
    public String ID;
    @JsonProperty(value = "dates", required = true)
    public List<String> dates;

}

