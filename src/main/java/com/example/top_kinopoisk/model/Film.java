package com.example.top_kinopoisk.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "films")
@Accessors(chain = true)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int position;

    @Column(name = "rating_value")
    private float ratingValue;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "count_votes")
    private int countVotes;

    @Column(name = "year")
    private int year;
}



