package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.response.ShowTimeSeatDetailResponse;
import com.dang.Movie_Ticket.entity.ShowTime;
import com.dang.Movie_Ticket.entity.ShowTimeSeat;
import com.dang.Movie_Ticket.util.enums.SeatStatus;

import java.util.List;

public interface ShowTimeSeatService {
    void createShowTimeSeat(String theaterId, ShowTime showTime);
    void changeShowTimeSeatStatus( String showTimeId, String seatId, SeatStatus status);
    void batchUpdateSeatStatus(String showTimeId, List<String> seatIds, SeatStatus status);
    List<ShowTimeSeatDetailResponse> findAllByShowTimeId(String showTimeId);
    boolean isSeatStillReserve(String showtimeId, String seatId);
    ShowTimeSeat findByShowTimeIdAndSeatId(String showTimeId, String seatId);

}
