package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmsService {
    private final FilmRepository filmRepository;

    public DataFromXml getFilmsByDate(int year) {
        if (year == -1) {
            return new DataFromXml().setFilms(filmRepository.findAll());
        }
        return new DataFromXml().setFilms(filmRepository.findAllByYear(year));
    }
}
