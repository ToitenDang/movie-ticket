package com.dang.Movie_Ticket.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invalidated")
public class InvalidatedToken {
    @Id
    private String id;

    @Column(name = "expiry_time")
    private Date expiryTime;
}
