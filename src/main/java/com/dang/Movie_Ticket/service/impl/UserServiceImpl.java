package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.entity.User;
import com.dang.Movie_Ticket.mapper.UserMapper;
import com.dang.Movie_Ticket.repository.UserRepository;
import com.dang.Movie_Ticket.service.UserService;
import com.dang.Movie_Ticket.util.enums.MembershipLevel;
import com.dang.Movie_Ticket.util.enums.Role;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO addUser(UserCreateDTO request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .phone(request.getPhone())
                .membershipLevel(MembershipLevel.BASIC)
                .status(UserStatus.ACTIVE)
                .role(Role.USER)
                .build();

        User userResponse = userRepository.save(user);
        return userMapper.toUserResponse(userResponse);
    }

    @Override
    public void updateUser(String userId, UserCreateDTO request) {

    }

    @Override
    public void changeUserStatus(String userId, UserStatus status) {

    }

    @Override
    public PageResponse<?> getUsers(int pageNo, int pageSize, String sortBy) {
        return null;
    }

    @Override
    public UserResponseDTO getUser(String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }
}
