package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.SeatResponse;
import com.dang.Movie_Ticket.dto.response.ShowTimeDetailResponse;
import com.dang.Movie_Ticket.entity.Seat;
import com.dang.Movie_Ticket.entity.ShowTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShowTimeMapper {
    ShowTimeDetailResponse toShowTimeDetailResponse(ShowTime showTime);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
