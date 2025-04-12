package com.dang.Movie_Ticket.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowTimeSeatId implements Serializable {
    private String showTimeId;
    private String seatId;
}
