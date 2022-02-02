package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.model.Film;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DataFromXml {
    private List<Film> films;
}

