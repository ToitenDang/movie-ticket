package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.SeatRequest;
import com.dang.Movie_Ticket.dto.response.SeatResponse;
import com.dang.Movie_Ticket.util.enums.SeatStatus;

import java.util.List;

public interface SeatService {
    void createSeat(SeatRequest request);
    void updateSeat(SeatRequest request);
    void changeSeatStatus(String theaterId, String seatId, SeatStatus status);

    List<SeatResponse> findAllByTheaterId(String theaterId);
}
