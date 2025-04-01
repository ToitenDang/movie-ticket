package com.dang.Movie_Ticket.dto.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String email;
    private String password;
}
