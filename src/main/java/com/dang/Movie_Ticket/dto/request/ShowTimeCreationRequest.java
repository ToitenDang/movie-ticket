package com.dang.Movie_Ticket.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ShowTimeCreationRequest {
    private String movieId;
    private String theaterId;
    private int price;
    private LocalDateTime startTime;
}
