package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.AuthenticationRequest;
import com.dang.Movie_Ticket.dto.request.IntrospectRequest;
import com.dang.Movie_Ticket.dto.request.LogoutRequest;
import com.dang.Movie_Ticket.dto.request.RefreshRequest;
import com.dang.Movie_Ticket.dto.response.AuthenticationResponse;
import com.dang.Movie_Ticket.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
