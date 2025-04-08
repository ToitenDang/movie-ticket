package com.dang.Movie_Ticket.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private String id;
    private String theaterId;
    private String seatNumber;
    private String status;
    //private Set<Seat> seats = new HashSet<>();
}
