package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.AuthenticationRequest;

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
}
