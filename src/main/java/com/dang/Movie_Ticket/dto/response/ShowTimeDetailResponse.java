package com.dang.Movie_Ticket.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowTimeDetailResponse {
    private String id;
    private MovieDetailResponse movie;
    private TheaterResponse theater;
    private int price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}
