package com.example.top_kinopoisk.repository;

import com.example.top_kinopoisk.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    List<Film> findAllByYear(int year);
}
