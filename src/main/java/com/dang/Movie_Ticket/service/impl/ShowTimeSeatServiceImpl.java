package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.response.SeatResponse;
import com.dang.Movie_Ticket.dto.response.ShowTimeSeatDetailResponse;
import com.dang.Movie_Ticket.entity.Seat;
import com.dang.Movie_Ticket.entity.ShowTime;
import com.dang.Movie_Ticket.entity.ShowTimeSeat;
import com.dang.Movie_Ticket.entity.ShowTimeSeatId;
import com.dang.Movie_Ticket.mapper.SeatMapper;
import com.dang.Movie_Ticket.repository.SeatRepository;
import com.dang.Movie_Ticket.repository.ShowTimeSeatRepository;
import com.dang.Movie_Ticket.service.SeatService;
import com.dang.Movie_Ticket.service.ShowTimeSeatService;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShowTimeSeatServiceImpl implements ShowTimeSeatService {
    private final SeatRepository seatRepository;
    private final ShowTimeSeatRepository showTimeSeatRepository;
    private final SeatMapper seatMapper;


    public ShowTimeSeatServiceImpl(SeatRepository seatRepository, ShowTimeSeatRepository showTimeSeatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.showTimeSeatRepository = showTimeSeatRepository;
        this.seatMapper = seatMapper;
    }

    @Override
    public void createShowTimeSeat(String theaterId, ShowTime showTime) {
        List<Seat> seats = this.seatRepository.findAllByTheaterId(theaterId);
        // Tạo danh sách ShowTimeSeat
        List<ShowTimeSeat> showTimeSeats = seats.stream()
                .map(seat -> ShowTimeSeat.builder()
                        .id(new ShowTimeSeatId(showTime.getId(), seat.getId()))
                        .showTime(showTime)
                        .seat(seat)
                        .status(seat.getStatus())
                        .build())
                .collect(Collectors.toList());

        // Lưu danh sách ShowTimeSeat
        this.showTimeSeatRepository.saveAll(showTimeSeats);
    }

    @Override
    public void changeShowTimeSeatStatus( String showTimeId, String seatId, SeatStatus status) {
        ShowTimeSeat seat = this.showTimeSeatRepository.findSeatByShowTimeIdAndSeatId(showTimeId, seatId);
        seat.setStatus(status);
        this.showTimeSeatRepository.save(seat);
    }

    @Override
    public List<ShowTimeSeatDetailResponse> findAllByShowTimeId(String showTimeId) {
        log.info("Get All Seat in ShowTime");
        List<ShowTimeSeat> seats = this.showTimeSeatRepository.findAllByShowTimeId(showTimeId);
        return seats.stream().map(seat ->{
            return ShowTimeSeatDetailResponse.builder()
                    .showTimeId(seat.getShowTime().getId())
                    .seat(seatMapper.toSeatResponse(seat.getSeat()))
                    .status(seat.getStatus().toString())
                    .build();
        }).toList();
    }

    @Override
    public boolean isSeatStillReserve(String showtimeId, String seatId) {
        ShowTimeSeat seat = showTimeSeatRepository.findSeatByShowTimeIdAndSeatId(showtimeId, seatId);
        return seat != null && SeatStatus.RESERVED.equals(seat.getStatus());
    }
}
