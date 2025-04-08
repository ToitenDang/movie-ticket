package com.dang.Movie_Ticket.dto.response;

import com.dang.Movie_Ticket.entity.Seat;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponse {
    private String id;
    private String name;
    private String location;
    private int totalSeats;
    //private Set<Seat> seats = new HashSet<>();
}
