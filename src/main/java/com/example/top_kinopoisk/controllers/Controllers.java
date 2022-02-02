package com.example.top_kinopoisk.controllers;

import com.example.top_kinopoisk.service.DataFromXml;
import com.example.top_kinopoisk.service.FilmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        DataFromXml filmsByDate = filmsService.getFilmsByDate(year);
        model.addAttribute("films", filmsByDate);
        return "topFilms.html";
    }

}
