package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.response.MovieDetailRespone;
import com.dang.Movie_Ticket.dto.response.TheaterResponse;
import com.dang.Movie_Ticket.entity.Movie;
import com.dang.Movie_Ticket.entity.Theater;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TheaterMapper {
    TheaterResponse toTheaterResponse(Theater theater);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
