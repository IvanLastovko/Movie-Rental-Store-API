package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Rentals {

    @JsonProperty("rentals")
    public List<Rental> rentalList;

}
