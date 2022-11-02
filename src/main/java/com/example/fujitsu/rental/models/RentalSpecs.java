package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RentalSpecs {
    @JsonProperty(value = "duration", required = true)
    public String duration;
    @JsonProperty(value = "startDate", required = true)
    public String startDate;
}
