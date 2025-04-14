package com.dang.Movie_Ticket.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodUpdateRequest {
    private String name;
    private int price;
    private int quantity;
    private int sold;
}
