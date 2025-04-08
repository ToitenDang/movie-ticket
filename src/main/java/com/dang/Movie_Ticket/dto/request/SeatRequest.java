package com.dang.Movie_Ticket.dto.request;

import com.dang.Movie_Ticket.entity.Theater;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatRequest {
    private String theaterId;
    private int totalSeat;
    private int seatsPerRow;
}
