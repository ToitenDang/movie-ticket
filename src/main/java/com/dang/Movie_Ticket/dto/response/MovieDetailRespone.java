package com.dang.Movie_Ticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieDetailRespone {
    private String id;
    private String title;
    private String genre;
    private int duration;
    private float rating;
    private LocalDate releaseDate;
    private String synopsis;
    private String poster;
    private String status;
}
