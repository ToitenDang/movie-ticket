package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.ShowTimeCreationRequest;
import com.dang.Movie_Ticket.dto.response.ShowTimeDetailResponse;
import com.dang.Movie_Ticket.dto.response.ShowTimeSeatDetailResponse;
import com.dang.Movie_Ticket.entity.ShowTime;
import com.dang.Movie_Ticket.entity.ShowTimeSeat;
import com.dang.Movie_Ticket.util.enums.MovieStatus;

import java.util.List;

public interface ShowTimeService {
    String addShowTime(ShowTimeCreationRequest request);
    ShowTimeDetailResponse getShowTime(String showTimeId);
    ShowTime getShowTimeById(String showTimeId);
    List<ShowTimeDetailResponse> getShowTimes();
    List<ShowTimeSeatDetailResponse> getAllShowTimeSeats(String showTimeId);
    void changeShowTimeStatus(String showTimeId, MovieStatus status);
    void deleteShowTime(String showTimeId);
    boolean existById(String showtimeId);
}
