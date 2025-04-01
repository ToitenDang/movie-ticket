package com.dang.Movie_Ticket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "createdAt")
    @CreationTimestamp
    private Date createAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Date updatedAt;
}
