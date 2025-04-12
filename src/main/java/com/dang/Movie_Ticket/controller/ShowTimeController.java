package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.dto.request.ShowTimeCreationRequest;
import com.dang.Movie_Ticket.entity.ShowTime;
import com.dang.Movie_Ticket.service.ShowTimeSeatService;
import com.dang.Movie_Ticket.service.ShowTimeService;
import com.dang.Movie_Ticket.util.enums.MovieStatus;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showtime")
@Slf4j
public class ShowTimeController {

    private final ShowTimeService showTimeService;
    private final ShowTimeSeatService showTimeSeatService;

    public ShowTimeController(ShowTimeService showTimeService, ShowTimeSeatService showTimeSeatService) {
        this.showTimeService = showTimeService;
        this.showTimeSeatService = showTimeSeatService;
    }

    @PostMapping
    public ResponseData<?> addShowTime(@RequestBody ShowTimeCreationRequest request){
        log.info("Add New ShowTime");
        return new ResponseData<>(HttpStatus.CREATED.value(), "Create showtime succeed",
                this.showTimeService.addShowTime(request));
    }

    @GetMapping
    public ResponseData<?> getShowTimes(){
        log.info("Get all showtime");
        return new ResponseData<>(HttpStatus.OK.value(), "ShowTimes", this.showTimeService.getShowTimes());
    }

    @GetMapping("/{showtimeId}")
    public ResponseData<?> getShowTime(@PathVariable String showtimeId){
        return new ResponseData<>(HttpStatus.OK.value(), "ShowTime", this.showTimeService.getShowTime(showtimeId));
    }

    @GetMapping("/seats/{showtimeId}")
    public ResponseData<?> getAllShowTimeSeats(@PathVariable String showtimeId){
        return new ResponseData<>(HttpStatus.OK.value(), "ShowTime", this.showTimeService.getAllShowTimeSeats(showtimeId));
    }

    @PatchMapping("/{showtimeId}")
    public ResponseData<?> changeShowTimeStatus(@PathVariable String showtimeId, @RequestParam MovieStatus status){
        log.info("Change showtime status");
        this.showTimeService.changeShowTimeStatus(showtimeId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Change showtime status succeed");
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseData<?> deleteShowTime(@PathVariable String showtimeId){
        log.info("Delete showtime");
        this.showTimeService.deleteShowTime(showtimeId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete showtime succeed");
    }

    @PatchMapping("/{showtimeId}/{seatId}")
    public ResponseData<?> changeShowTimeSeatStatus(@PathVariable String showtimeId,
                                                    @PathVariable String seatId,
                                                    @RequestParam SeatStatus status){
        this.showTimeSeatService.changeShowTimeSeatStatus(showtimeId, seatId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Change ShowTime Seat Status");
    }
}
