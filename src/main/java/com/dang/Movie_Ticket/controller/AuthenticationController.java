package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.AuthenticationRequest;
import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.dto.request.ResponseError;
import com.dang.Movie_Ticket.dto.response.AuthenticationResponse;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ResponseData<?> login(@RequestBody AuthenticationRequest request){
      log.info("Login");
      try {
          AuthenticationResponse auth = AuthenticationResponse.builder()
                  .authenticated(authenticationService.authenticate(request))
                  .build();
          return new ResponseData<>(HttpStatus.OK.value(), "Login succeed", auth);
      } catch (AppException e){
          log.error("Error message {}", e.getMessage(), e.getCause());
          return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Login failed");
      }
    }
}
