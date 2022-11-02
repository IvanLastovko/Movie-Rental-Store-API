package com.example.fujitsu.rental.utils;

import com.example.fujitsu.rental.models.Movies;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ReadJSONFile {

    public static Movies readJson(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());

        return objectMapper.readValue(resource.getFile(), Movies.class);
    }
}
