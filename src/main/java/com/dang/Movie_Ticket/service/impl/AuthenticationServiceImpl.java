package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.AuthenticationRequest;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Override
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
