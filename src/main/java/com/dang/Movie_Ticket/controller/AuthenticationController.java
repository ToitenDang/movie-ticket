package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.*;
import com.dang.Movie_Ticket.dto.response.AuthenticationResponse;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/log-in")
    public ResponseData<?> login(@RequestBody AuthenticationRequest request){
      return new ResponseData<>(HttpStatus.OK.value(), "Log-in succeed",
              this.authenticationService.authenticate(request));
    }

    @PostMapping("/token")
    public ResponseData<?> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
      return new ResponseData<>(HttpStatus.OK.value(), "Result introspect",
              this.authenticationService.introspect(request));
    }
    @PostMapping("/logout")
    public ResponseData<?> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        log.info("Logout user");
        this.authenticationService.logout(request);
        return new ResponseData<>(HttpStatus.OK.value(), "Logout succeed");
    }

    @PostMapping("/refresh")
    public ResponseData<?> refreshToken(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        log.info("Refresh token of user");
        return new ResponseData<>(HttpStatus.OK.value(), "Refresh token succeed",
                this.authenticationService.refreshToken(request));
    }
}
