package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.util.enums.UserStatus;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO addUser(UserCreateDTO request);
    void updateUser(String userId, UserCreateDTO request);
    void changeUserStatus(String userId, UserStatus status);
    PageResponse<?> getUsers(int pageNo, int pageSize, String sortBy);
    UserResponseDTO getUser(String userId);
    void deleteUser(String userId);
}
