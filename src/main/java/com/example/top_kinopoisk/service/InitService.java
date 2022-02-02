package com.example.top_kinopoisk.service;

import com.example.top_kinopoisk.repository.FilmRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitService {
    private final static String KINOPOISK_TOP = "http://www.kinopoisk.ru/top/";

    @Bean
    ApplicationRunner init(FilmRepository filmRepository) {
        return init -> {
            DataFromXml dataFromXml = parseKinopoisk();
        };
    }

    private DataFromXml parseKinopoisk() {
        try {
            Document doc = Jsoup.connect(KINOPOISK_TOP).get();
            Elements elements = doc.getElementsByClass("desktop-rating-selection-film-item");
//            System.out.println(elements.get(0));
            for (int i = 0; i < elements.size(); i++) {
                Elements e = elements.get(i).getElementsByAttributeValue("class", "selection-film-item-meta__name");
                System.out.println(e);
                System.out.println(e.attr("p"));
            }
//            System.out.println(elements);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataFromXml();
    }
}
