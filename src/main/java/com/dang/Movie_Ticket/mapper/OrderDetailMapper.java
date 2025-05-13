package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.OrderDetailResponse;
import com.dang.Movie_Ticket.dto.response.ShowTimeDetailResponse;
import com.dang.Movie_Ticket.entity.OrderDetail;
import com.dang.Movie_Ticket.entity.ShowTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateMovie(@MappingTarget Movie movie, MovieUpdateRequest request);
}
