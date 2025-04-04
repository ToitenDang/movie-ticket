package com.dang.Movie_Ticket.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieUpdateRequest {

    private String title;

    private String genre;

    private Integer duration;

    //private float rating;

    private LocalDate releaseDate;
    private String poster;
    private String synopsis;
}
