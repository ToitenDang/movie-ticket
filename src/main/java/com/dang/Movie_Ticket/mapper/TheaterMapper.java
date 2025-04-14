package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.TheaterResponse;
import com.dang.Movie_Ticket.entity.Theater;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TheaterMapper {
    TheaterResponse toTheaterResponse(Theater theater);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
