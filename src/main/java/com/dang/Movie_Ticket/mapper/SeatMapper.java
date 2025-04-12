package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.SeatResponse;
import com.dang.Movie_Ticket.entity.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatResponse toSeatResponse(Seat seat);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
