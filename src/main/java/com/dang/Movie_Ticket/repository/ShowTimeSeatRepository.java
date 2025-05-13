package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.entity.Seat;
import com.dang.Movie_Ticket.entity.ShowTimeSeat;
import com.dang.Movie_Ticket.entity.ShowTimeSeatId;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeSeatRepository extends JpaRepository<ShowTimeSeat, ShowTimeSeatId> {
    ShowTimeSeat findSeatByShowTimeIdAndSeatId(String showTimeId, String seatId);
    List<ShowTimeSeat> findAllByShowTimeId(String showTimeId);

    @Modifying
    @Transactional
    @Query("UPDATE ShowTimeSeat s SET s.status = :status WHERE s.id.showTimeId = :showTimeId AND s.id.seatId IN :seatIds")
    void batchUpdateSeatStatus(@Param("showTimeId") String showTimeId,
                               @Param("seatIds") List<String> seatIds,
                               @Param("status") SeatStatus status);
}
