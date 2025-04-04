package com.dang.Movie_Ticket.dto.request;

import com.dang.Movie_Ticket.util.enums.MovieStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieCreationRequest {

    private String title;

    private String genre;

    private int duration;

    //private float rating;

    private LocalDate releaseDate;

    private String synopsis;
}
