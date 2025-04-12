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
    private MovieDetailRespone movie;
    private TheaterResponse theater;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}
