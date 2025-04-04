package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.MovieCreationRequest;
import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.service.MovieService;
import com.dang.Movie_Ticket.util.enums.MovieStatus;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("movies")
@Slf4j
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseData<String> addMovie(@ModelAttribute MovieCreationRequest request,
                                 @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("Add movie with request: {}", request.getTitle());
        return new ResponseData<>(HttpStatus.CREATED.value(), "Add movie succeed",
                this.movieService.addMovie(request, file));
    }

    @PutMapping("/{movieId}")
    public ResponseData<?> updateMovie( @PathVariable String movieId, @ModelAttribute MovieUpdateRequest request,
                            @RequestParam(required = false) MultipartFile file) throws IOException {
        log.info("Update movie");
        movieService.updateMovie(movieId, request, file);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Movie update succeed");
    }

    @PatchMapping("/{movieId}")
    public ResponseData<?> changeMovieStatus(@PathVariable String movieId, @RequestParam MovieStatus movieStatus){
        log.info("Change movie status");
        this.movieService.changeStatus(movieId, movieStatus);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Change movie status succeed");
    }

    @GetMapping
    public ResponseData<?> getMovies(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                     @Min(1) @RequestParam(defaultValue = "20", required = false)
                                     int pageSize,
                                     @RequestParam(required = false) String sortBy){
        log.info("Get list movie");
        return new ResponseData<>(HttpStatus.OK.value(), "Movies",
                this.movieService.getAllMovies(pageNo, pageSize, sortBy));
    }

    @GetMapping("/{movieId}")
    public ResponseData<?> getMovie(@PathVariable String movieId){
        log.info("Movie movie by id: {}", movieId);
        return new ResponseData<>(HttpStatus.OK.value(), "Movie", this.movieService.getMovie(movieId));
    }

    @DeleteMapping("/{movieId}")
    public ResponseData<?> deleteMovie(@PathVariable String movieId){
        log.info("Delete movie by id: {}", movieId);
        this.movieService.deleteMovie(movieId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete movie succeed");
    }
}
