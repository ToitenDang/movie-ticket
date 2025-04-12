package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "showtimeseat")
public class ShowTimeSeat {
    @EmbeddedId
    private ShowTimeSeatId id;

    @ManyToOne
    @MapsId("showTimeId")
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;

    @ManyToOne
    @MapsId("seatId")
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(name = "createdAt")
    @CreationTimestamp
    private Date createAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Date updatedAt;
}
