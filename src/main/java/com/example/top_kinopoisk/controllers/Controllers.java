package com.example.top_kinopoisk.controllers;

import com.example.top_kinopoisk.service.DataFromXml;
import com.example.top_kinopoisk.service.FilmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class Controllers {
    private final FilmsService filmsService;

    @GetMapping
    public ResponseEntity<DataFromXml> getFilmsByDate(@RequestParam(name = "year", defaultValue = "-1") int year) {
        return new ResponseEntity<>(filmsService.getFilmsByDate(year), HttpStatus.OK);
    }

}
