package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.model.Film;
import lombok.Data;

import java.util.List;

@Data
public class DataFromXml {
    private List<Film> films;
}

