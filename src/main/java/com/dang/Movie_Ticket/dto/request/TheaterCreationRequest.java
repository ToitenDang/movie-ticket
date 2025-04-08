package com.dang.Movie_Ticket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheaterCreationRequest {
    private String name;

    private String location;

    private int totalSeats;
}
