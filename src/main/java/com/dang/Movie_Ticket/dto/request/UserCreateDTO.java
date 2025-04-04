package com.dang.Movie_Ticket.dto.request;
import com.dang.Movie_Ticket.validator.DobConstraint;
import com.dang.Movie_Ticket.validator.ValidatePattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

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
