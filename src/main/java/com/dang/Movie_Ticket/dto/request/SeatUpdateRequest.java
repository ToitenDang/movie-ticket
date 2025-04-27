package com.dang.Movie_Ticket.dto.request;

import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatUpdateRequest {
    private String showtimeId;
    private String seatId;
    private SeatStatus status;

}
