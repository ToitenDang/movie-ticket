package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.dto.response.MovieDetailResponse;
import com.dang.Movie_Ticket.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("SELECT m from Movie m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Movie> searchMovies(String keyword, Pageable pageable);
}
