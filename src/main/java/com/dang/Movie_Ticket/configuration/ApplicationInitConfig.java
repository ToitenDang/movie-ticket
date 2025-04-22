package com.dang.Movie_Ticket.configuration;

import com.dang.Movie_Ticket.entity.User;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.Role;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (userRepository.findUserByEmail(ADMIN_USER_NAME + "@gmail.com").isEmpty()){
                User user = User.builder()
                        .name("Dang The Ky")
                        .email(ADMIN_USER_NAME + "@gmail.com")
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .dob(LocalDate.of(2003, 3, 25))
                        .phone("0123456789")
                        .membershipLevel(MembershipLevel.VIP)
                        .status(UserStatus.ACTIVE)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    }
}