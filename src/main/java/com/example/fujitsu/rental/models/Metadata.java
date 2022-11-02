package com.example.fujitsu.rental.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class Metadata {

    public String category;
    @JsonFormat(pattern="dd.MM.yyyy")
    public DateTime releaseDate;
    public List<String> actors;
    public String description;
    public double price;

}
