package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.MovieStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "showtime")
public class ShowTime extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Column(name = "price")
    private int price;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name= "status")
    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ShowTimeSeat> showTimeSeats; // Quan hệ 1-n với ShowTimeSeat

}
