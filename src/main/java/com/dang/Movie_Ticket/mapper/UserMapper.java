package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.request.UserCreateDTO;
import com.dang.Movie_Ticket.dto.response.UserResponseDTO;
import com.dang.Movie_Ticket.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserCreateDTO request);
}
