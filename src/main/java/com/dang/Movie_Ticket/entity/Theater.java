package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.TheaterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "theater")
public class Theater extends AbstractEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "total_seats")
    private int totalSeats;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TheaterStatus status;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats;

}
