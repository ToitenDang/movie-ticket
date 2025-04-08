package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.SeatStatus;
import com.dang.Movie_Ticket.util.enums.TheaterStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
public class Seat extends AbstractEntity{
//    @Column(name = "theater_id")
//    private String theaterId;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

}
