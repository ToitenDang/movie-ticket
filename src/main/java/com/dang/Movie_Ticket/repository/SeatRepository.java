package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    @Transactional
    void deleteSeatsByTheaterId(String theaterId);

    Seat findSeatByTheaterIdAndId(String theaterId, String seatId);
    List<Seat> findAllByTheaterId(String theaterId);
}
