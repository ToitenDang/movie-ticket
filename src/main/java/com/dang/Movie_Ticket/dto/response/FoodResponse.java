package com.dang.Movie_Ticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FoodResponse {
    private String id;
    private String name;
    private int price;
    private int sold;
    private String image;
    private String status;
}
