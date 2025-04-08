package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.SeatRequest;
import com.dang.Movie_Ticket.dto.response.SeatResponse;
import com.dang.Movie_Ticket.entity.Seat;
import com.dang.Movie_Ticket.entity.Theater;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.SeatRepository;
import com.dang.Movie_Ticket.repository.TheaterRepository;
import com.dang.Movie_Ticket.service.SeatService;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final TheaterRepository theaterRepository;

    public SeatServiceImpl(SeatRepository seatRepository,
                           TheaterRepository theaterRepository) {
        this.seatRepository = seatRepository;
        this.theaterRepository = theaterRepository;
    }

    @Override
    public void createSeat(SeatRequest request) {
        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new AppException(ErrorCode.THEATER_NOT_EXISTED));
        int rows = (int) Math.ceil((double) request.getTotalSeat() / request.getSeatsPerRow());

        List<Seat> batch = new ArrayList<>();
        for (char row = 'A'; row < 'A' + rows; row++) {
            for (int num = 1; num <= request.getSeatsPerRow(); num++) {
                if (batch.size() >= 50) {
                    seatRepository.saveAll(batch);
                    batch.clear();
                }
                String seatNumber = row + String.valueOf(num);
                batch.add(Seat.builder()
                        .seatNumber(seatNumber)
                        .status(SeatStatus.EMPTY)
                        .theater(theater)
                        .build());
            }
        }
        if (!batch.isEmpty()) seatRepository.saveAll(batch);
    }

    @Override
    public void updateSeat(SeatRequest request) {
        this.seatRepository.deleteSeatsByTheaterId(request.getTheaterId());
        createSeat(request);
        log.info("Generate new list seat");
    }

    @Override
    public void changeSeatStatus(String theaterId, String seatId, SeatStatus status) {
        Seat seat = this.seatRepository.findSeatByTheaterIdAndId(theaterId, seatId);
        seat.setStatus(status);
        this.seatRepository.save(seat);

        log.info("Change Seat Status");
    }

    @Override
    public List<SeatResponse> findAllByTheaterId(String theaterId) {
        List<Seat> seats = this.seatRepository.findAllByTheaterId(theaterId);

        return seats.stream().map(seat -> {
            return SeatResponse.builder()
                    .id(seat.getId())
                    .theaterId(seat.getTheater().getId())
                    .seatNumber(seat.getSeatNumber())
                    .status(seat.getStatus().toString())
                    .build();
        }).toList();
    }

}
