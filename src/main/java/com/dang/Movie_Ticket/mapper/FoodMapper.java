package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.response.FoodResponse;
import com.dang.Movie_Ticket.dto.response.MovieDetailResponse;
import com.dang.Movie_Ticket.entity.Food;
import com.dang.Movie_Ticket.entity.Movie;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodResponse toFoodResponse(Food food);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
