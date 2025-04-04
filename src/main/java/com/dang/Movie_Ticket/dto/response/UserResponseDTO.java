package com.dang.Movie_Ticket.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private String role;
    private String status;
}
