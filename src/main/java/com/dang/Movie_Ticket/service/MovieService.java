package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.MovieCreationRequest;
import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.response.MovieDetailResponse;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.util.enums.MovieStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MovieService {
    String addMovie(MovieCreationRequest request, MultipartFile file) throws IOException;

    MovieDetailResponse getMovie(String movieId);
    PageResponse<?> getAllMovies(int pageNo, int pageSize, String sortBy);
    PageResponse<?> searchMovie(int pageNo, int pageSize, String sortBy, String keyword);
    void updateMovie(String movieId, MovieUpdateRequest request, MultipartFile file) throws IOException;
    void deleteMovie(String movieId);
    void changeStatus(String movieId, MovieStatus status);
}
