package com.dang.Movie_Ticket.dto.request;

import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import com.dang.Movie_Ticket.validator.DobConstraint;
import com.dang.Movie_Ticket.validator.ValidatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class UserCreateDTO {
    private String name;

    @ValidatePattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "EMAIL_INVALID")
    private String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    //@JsonFormat(pattern = "MM/dd/yyyy")
    @DobConstraint(min = 16, message = "DOB_INVALID")
    private LocalDate dob;

    @ValidatePattern(regexp = "^\\d{10}$", message = "PHONE_INVALID")
    private String phone;
}
