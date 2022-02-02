package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.model.Film;
import com.example.top_kinopoisk.repository.FilmRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InitService {
    private final static String KINOPOISK_TOP = "https://www.kinopoisk.ru/lists/top250/?tab=all";

    @Bean
    ApplicationRunner init(FilmRepository filmRepository) {
        Optional<Film> optionalFilm = filmRepository.findAll().stream().findAny();
        if (optionalFilm.isEmpty()) {
            return init -> {
                DataFromXml dataFromXml = parseKinopoisk();
                filmRepository.saveAll(dataFromXml.getFilms());
            };
        }
        return null;
    }

    private DataFromXml parseKinopoisk() {
        List<Film> films = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(KINOPOISK_TOP)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36")
                    .timeout(5000).get();
            Elements elements = doc.getElementsByClass("desktop-rating-selection-film-item");
            for (int i = 0; i < elements.size(); i++) {
                Elements originalName = elements.get(i).getElementsByAttributeValue("class", "selection-film-item-meta__original-name");
                Elements position = elements.get(i).getElementsByAttributeValue("class", "film-item-rating-position__position");
                Elements ratingValue = elements.get(i).getElementsByAttributeValue("class", "rating__value rating__value_positive");
                Elements countVotes = elements.get(i).getElementsByAttributeValue("class", "rating__count");
                Elements name = elements.get(i).getElementsByAttributeValue("class", "selection-film-item-meta__name");
                String[] split = originalName.text().split(",");
                String votes = countVotes.text().replace(" ", "");
                int v = Integer.parseInt(votes);
                Film film = new Film()
                        .setPosition(Integer.parseInt(position.text()))
                        .setRatingValue(Float.parseFloat(ratingValue.text()))
                        .setCountVotes(v);
                if (split.length > 2) {
                    film.setYear(Integer.parseInt(split[2].replace(" ", "")));
                } else if (split.length == 2) {
                    film.setOriginalName(split[0]);
                    film.setYear(Integer.parseInt(split[1].replace(" ", "")));
                } else {
                    film.setOriginalName(name.text());
                    film.setYear(Integer.parseInt(split[0].replace(" ", "")));
                }
                films.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataFromXml().setFilms(films);
    }
}
