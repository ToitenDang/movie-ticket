package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.TheaterCreationRequest;
import com.dang.Movie_Ticket.dto.response.TheaterResponse;
import com.dang.Movie_Ticket.util.enums.TheaterStatus;

import java.util.List;

public interface TheaterService {
    String createTheater(TheaterCreationRequest request);
    void updateTheater(String theaterId, TheaterCreationRequest request);
    void changeTheaterStatus(String theaterId, TheaterStatus status);
    TheaterResponse getTheater(String theaterId);
    List<TheaterResponse> getTheaters();
}
