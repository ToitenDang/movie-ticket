package com.dang.Movie_Ticket.mapper;

import com.dang.Movie_Ticket.dto.response.OrderResponse;
import com.dang.Movie_Ticket.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "showTimeId", source = "showTime.id")
    OrderResponse toOrderResponse(Order order);

}
