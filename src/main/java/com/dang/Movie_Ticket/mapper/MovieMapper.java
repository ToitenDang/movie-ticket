package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.request.MovieCreationRequest;
import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.response.MovieDetailRespone;
import com.dang.Movie_Ticket.entity.Movie;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDetailRespone toMovieResponse(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
