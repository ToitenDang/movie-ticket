package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.ShowTimeCreationRequest;
import com.dang.Movie_Ticket.dto.response.ShowTimeDetailResponse;
import com.dang.Movie_Ticket.dto.response.ShowTimeSeatDetailResponse;
import com.dang.Movie_Ticket.entity.*;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.mapper.MovieMapper;
import com.dang.Movie_Ticket.mapper.TheaterMapper;
import com.dang.Movie_Ticket.repository.MovieRepository;
import com.dang.Movie_Ticket.repository.ShowTimeRepository;
import com.dang.Movie_Ticket.repository.ShowTimeSeatRepository;
import com.dang.Movie_Ticket.repository.TheaterRepository;
import com.dang.Movie_Ticket.service.ShowTimeSeatService;
import com.dang.Movie_Ticket.service.ShowTimeService;
import com.dang.Movie_Ticket.util.enums.MovieStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShowTimeServiceImpl implements ShowTimeService {
    private final TheaterMapper theaterMapper;
    private final MovieMapper movieMapper;

    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ShowTimeSeatRepository showTimeSeatRepository;
    private final ShowTimeSeatService showTimeSeatService;

    public ShowTimeServiceImpl(ShowTimeRepository showTimeRepository, MovieRepository movieRepository,
                               TheaterRepository theaterRepository, ShowTimeSeatRepository showTimeSeatRepository,
                               ShowTimeSeatService showTimeSeatService,
                               MovieMapper movieMapper,
                               TheaterMapper theaterMapper) {
        this.showTimeRepository = showTimeRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.showTimeSeatRepository = showTimeSeatRepository;
        this.showTimeSeatService = showTimeSeatService;
        this.movieMapper = movieMapper;
        this.theaterMapper = theaterMapper;
    }

    @Override
    public String addShowTime(ShowTimeCreationRequest request) {
        Movie movie = this.movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        Theater theater = this.theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new AppException(ErrorCode.THEATER_NOT_EXISTED));

        ShowTime showTime = ShowTime.builder()
                .movie(movie)
                .theater(theater)
                .startTime(request.getStartTime())
                .endTime(request.getStartTime().plusMinutes(movie.getDuration()))
                .status(MovieStatus.COMING_SOON)
                .build();
        this.showTimeRepository.save(showTime);
        this.showTimeSeatService.createShowTimeSeat(theater.getId(), showTime);

        return showTime.getId();
    }

    @Override
    public ShowTimeDetailResponse getShowTime(String showTimeId) {
        ShowTime showTime = getShowTimeById(showTimeId);
        return ShowTimeDetailResponse.builder()
                .id(showTime.getId())
                .movie(movieMapper.toMovieResponse(showTime.getMovie()))
                .theater(theaterMapper.toTheaterResponse(showTime.getTheater()))
                .startTime(showTime.getStartTime())
                .endTime(showTime.getEndTime())
                .status(showTime.getStatus().toString())
                .build();
    }

    @Override
    public List<ShowTimeDetailResponse> getShowTimes() {
        log.info("Get all showtime");
        List<ShowTime> showTimes = this.showTimeRepository.findAll();
        return showTimes.stream().map(showTime -> {
            return ShowTimeDetailResponse.builder()
                    .id(showTime.getId())
                    .movie(movieMapper.toMovieResponse(showTime.getMovie()))
                    .theater(theaterMapper.toTheaterResponse(showTime.getTheater()))
                    .startTime(showTime.getStartTime())
                    .endTime(showTime.getEndTime())
                    .status(showTime.getStatus().toString())
                    .build();
        }).toList();
    }

    @Override
    public List<ShowTimeSeatDetailResponse> getAllShowTimeSeats(String showTimeId) {
        return  this.showTimeSeatService.findAllByShowTimeId(showTimeId);
    }

    @Override
    public void changeShowTimeStatus(String showTimeId, MovieStatus status) {
        ShowTime showTime = getShowTimeById(showTimeId);
        showTime.setStatus(status);
        this.showTimeRepository.save(showTime);
        log.info("Change showtime status {}", status);
    }

    @Override
    public void deleteShowTime(String showTimeId) {
        this.showTimeRepository.deleteById(showTimeId);
        log.info("Delete showtime");
    }

    private ShowTime getShowTimeById(String showtimeId){
        return this.showTimeRepository.findById(showtimeId)
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_EXISTED));
    }
}
