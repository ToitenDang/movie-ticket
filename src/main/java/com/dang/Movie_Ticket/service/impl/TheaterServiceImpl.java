package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.SeatRequest;
import com.dang.Movie_Ticket.dto.request.TheaterCreationRequest;
import com.dang.Movie_Ticket.dto.response.TheaterResponse;
import com.dang.Movie_Ticket.entity.Theater;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.TheaterRepository;
import com.dang.Movie_Ticket.service.SeatService;
import com.dang.Movie_Ticket.service.TheaterService;
import com.dang.Movie_Ticket.util.enums.TheaterStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TheaterServiceImpl implements TheaterService {
    @Value("${seatsPerRow}")
    private int seatsPerRow;

    private final TheaterRepository theaterRepository;
    private final SeatService seatService;

    public TheaterServiceImpl(TheaterRepository theaterRepository, SeatService seatService) {
        this.theaterRepository = theaterRepository;
        this.seatService = seatService;
    }

    @Override
    public String createTheater(TheaterCreationRequest request) {


        Theater theater = Theater.builder()
                .name(request.getName())
                .location(request.getLocation())
                .totalSeats(request.getTotalSeats())
                .status(TheaterStatus.ACTIVE)
                .build();
        this.theaterRepository.save(theater);
        SeatRequest seatRequest = SeatRequest.builder()
                .theaterId(theater.getId())
                .totalSeat(theater.getTotalSeats())
                .seatsPerRow(seatsPerRow)
                .build();
        this.seatService.createSeat(seatRequest);

        log.info("New Theater have id: {}", theater.getId());
        return theater.getId();
    }

    @Override
    public void updateTheater(String theaterId, TheaterCreationRequest request) {
        Theater theater = getTheaterById(theaterId);

        if (!theater.getStatus().equals(TheaterStatus.MAINTENANCE))
                throw new AppException(ErrorCode.THEATER_STATUS_INVALID);

        theater.setName(request.getName());
        theater.setLocation(request.getLocation());
        if (theater.getTotalSeats() != request.getTotalSeats()){
            theater.setTotalSeats(request.getTotalSeats());
            SeatRequest seatRequest = SeatRequest.builder()
                    .theaterId(theater.getId())
                    .totalSeat(theater.getTotalSeats())
                    .seatsPerRow(seatsPerRow)
                    .build();
            this.seatService.updateSeat(seatRequest);
        }
        this.theaterRepository.save(theater);
    }

    @Override
    public void changeTheaterStatus(String theaterId, TheaterStatus status) {
        Theater theater = getTheaterById(theaterId);
        theater.setStatus(status);

        log.info("Update Theater Status");
        this.theaterRepository.save(theater);
    }

    @Override
    public TheaterResponse getTheater(String theaterId) {
        Theater theater = getTheaterById(theaterId);

        log.info("Get Theater By Id");
        return TheaterResponse.builder()
                .id(theater.getId())
                .name(theater.getName())
                .location(theater.getLocation())
                .totalSeats(theater.getTotalSeats())
                //.seats(theater.getSeats())
                .build();
    }

    @Override
    public List<TheaterResponse> getTheaters() {
        List<Theater> theaters = theaterRepository.findAll();

        log.info("Get All Theater");
        return theaters.stream().map(theater -> {
            return TheaterResponse.builder()
                    .id(theater.getId())
                    .name(theater.getName())
                    .location(theater.getLocation())
                    .totalSeats(theater.getTotalSeats())
                    .build();
        }).collect(Collectors.toList());
    }

    private Theater getTheaterById(String theaterId){
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new AppException(ErrorCode.THEATER_NOT_EXISTED));
    }
}
