package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponse(User user);
}
