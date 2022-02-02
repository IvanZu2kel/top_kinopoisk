package com.example.top_kinopoisk.controllers;

import com.example.top_kinopoisk.model.Film;
import com.example.top_kinopoisk.service.DataFromXml;
import com.example.top_kinopoisk.service.FilmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Controllers {
    private final FilmsService filmsService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        String message = "";
        model.addAttribute("message", message);

        return "index.html";
    }

    @RequestMapping(value = { "/topFilms" }, method = RequestMethod.GET)
    public String getFilmsByDate(@RequestParam(name = "year", defaultValue = "-1") int year,
                                                      Model model) {
        List<Film> films = filmsService.getFilmsByDate(year).getFilms();
        model.addAttribute("films", films);
        return "topFilms.html";
    }

}
