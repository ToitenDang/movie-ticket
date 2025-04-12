package com.dang.Movie_Ticket.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowTimeSeatDetailResponse {
    private String showTimeId;
    private SeatResponse seat;
    private String status;
}
