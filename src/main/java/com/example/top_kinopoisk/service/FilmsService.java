package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.model.Film;
import com.example.top_kinopoisk.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmsService {
    private final FilmRepository filmRepository;

    @Cacheable(cacheNames = "filmsCache", key = "#p0")
    public DataFromXml getFilmsByDate(int year) {
        List<Film> all = filmRepository.findAll();
        if (all.size() == 0) {
            new InitService().init(filmRepository);
        }
        if (year == -1 || year == 0) {
            return new DataFromXml().setFilms(all.stream().limit(10).toList());
        }
        return new DataFromXml().setFilms(all.stream().filter(f -> f.getYear() == year).limit(10).toList());
    }
}
