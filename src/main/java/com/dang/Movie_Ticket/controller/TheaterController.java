package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.dto.request.TheaterCreationRequest;
import com.dang.Movie_Ticket.service.SeatService;
import com.dang.Movie_Ticket.service.TheaterService;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import com.dang.Movie_Ticket.util.enums.TheaterStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theaters")
@Slf4j
public class TheaterController {

    private final TheaterService theaterService;
    private final SeatService seatService;

    public TheaterController(TheaterService theaterService, SeatService seatService) {
        this.theaterService = theaterService;
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseData<?> createTheater(@RequestBody TheaterCreationRequest request){
        log.info("Create New Theater");
        return new ResponseData<>(HttpStatus.CREATED.value(),
                "Create theater success", theaterService.createTheater(request));
    }

    @GetMapping("/{theaterId}")
    public ResponseData<?> getTheater(@PathVariable String theaterId){
        log.info("Get Theater By Id: {}", theaterId);
        return new ResponseData<>(HttpStatus.OK.value(), "Theater", this.theaterService.getTheater(theaterId));
    }

    @GetMapping
    public ResponseData<?> getTheaters(){
        log.info("Get All Theater");
        return new ResponseData<>(HttpStatus.OK.value(), "Theaters", this.theaterService.getTheaters());
    }

    @PutMapping("/{theaterId}")
    public ResponseData<?> updateTheater(@PathVariable String theaterId,
                                         @RequestBody TheaterCreationRequest request){
        log.info("Update Theater");
        this.theaterService.updateTheater(theaterId, request);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Updated Theater");
    }

    @PatchMapping("/{theaterId}")
    public ResponseData<?> changeTheaterStatus(@PathVariable String theaterId,
                                               @RequestParam TheaterStatus status){
        log.info("Change Theater Status");
        this.theaterService.changeTheaterStatus(theaterId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Changed Theater Status");
    }

    @PatchMapping("/seats/{theaterId}/{seatId}")
    public ResponseData<?> changeSeatStatus(@PathVariable String theaterId,
                                            @PathVariable String seatId,
                                               @RequestParam SeatStatus status){
        log.info("Change Seat Status");
        this.seatService.changeSeatStatus(theaterId, seatId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Changed Seat Status");
    }

    @GetMapping("/seats/{theaterId}")
    public ResponseData<?> getSeatsByTheaterId(@PathVariable String theaterId){
        log.info("Get All Seat");
        return new ResponseData<>(HttpStatus.OK.value(), "Seats",
                this.seatService.findAllByTheaterId(theaterId));
    }
}
