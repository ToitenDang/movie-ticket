package com.dang.Movie_Ticket.dto.request;

import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dob;
    private String phone;
}
