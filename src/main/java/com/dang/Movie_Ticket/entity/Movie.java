package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie extends AbstractEntity{
    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "duration")
    private int duration;

    @Column(name = "rating")
    private float rating;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "poster")
    private String poster;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MovieStatus status;
}
