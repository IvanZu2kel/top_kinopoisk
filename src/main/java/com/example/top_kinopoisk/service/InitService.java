package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.model.Film;
import com.example.top_kinopoisk.repository.FilmRepository;
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
    private final static String KINOPOISK_TOP = "https://www.kinopoisk.ru/lists/top250/?page=";

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
        for (int j = 1; j < 6; j++) {
            try {
                Document doc = Jsoup.connect(KINOPOISK_TOP + j)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                        .referrer("https://www.google.com/")
                        .timeout(5000).get();
                Elements elements = doc.getElementsByClass("desktop-rating-selection-film-item");
                for (org.jsoup.nodes.Element element : elements) {
                    Elements originalName = element.getElementsByAttributeValue("class", "selection-film-item-meta__original-name");
                    Elements position = element.getElementsByAttributeValue("class", "film-item-rating-position__position");
                    Elements ratingValue = element.getElementsByAttributeValue("class", "rating__value rating__value_positive");
                    Elements countVotes = element.getElementsByAttributeValue("class", "rating__count");
                    Elements name = element.getElementsByAttributeValue("class", "selection-film-item-meta__name");
                    Film film = createFilm(originalName, position, ratingValue, countVotes, name);
                    films.add(film);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(films.stream().findAny());
        return new DataFromXml().setFilms(films);
    }

    private Film createFilm(Elements originalName, Elements position, Elements ratingValue, Elements countVotes, Elements name) {
        String[] split = originalName.text().split(",");
        String votes = countVotes.text().replace(" ", "");
        int v = Integer.parseInt(votes);
        Film film = new Film()
                .setPosition(Integer.parseInt(position.text()))
                .setRatingValue(Float.parseFloat(ratingValue.text()))
                .setCountVotes(v);
        if (split.length == 4) {
            film.setOriginalName(split[0] + ", " + split[1] + ", " + split[2]);
            film.setYear(Integer.parseInt(split[3].replace(" ", "")));
        } else if (split.length == 3) {
            film.setOriginalName(split[0]);
            film.setYear(Integer.parseInt(split[2].replace(" ", "")));
        } else if (split.length == 2) {
            film.setOriginalName(split[0]);
            film.setYear(Integer.parseInt(split[1].replace(" ", "")));
        } else if (split.length == 1) {
            film.setOriginalName(name.text());
            film.setYear(Integer.parseInt(split[0].replace(" ", "")));
        }
        return film;
    }
}
