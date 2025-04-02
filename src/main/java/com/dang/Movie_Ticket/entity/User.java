package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.Role;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends AbstractEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "phone")
    private String phone;

    @Column(name = "membership_level")
    @Enumerated(EnumType.STRING)
    private MembershipLevel membershipLevel;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
