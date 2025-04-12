package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.entity.Seat;
import com.dang.Movie_Ticket.entity.ShowTimeSeat;
import com.dang.Movie_Ticket.entity.ShowTimeSeatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeSeatRepository extends JpaRepository<ShowTimeSeat, ShowTimeSeatId> {
    ShowTimeSeat findSeatByShowTimeIdAndSeatId(String showTimeId, String seatId);
    List<ShowTimeSeat> findAllByShowTimeId(String showTimeId);
}
