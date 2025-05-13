package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, String> {
    boolean existsById(String showtimeId);
}
