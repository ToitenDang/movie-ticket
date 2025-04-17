package com.dang.Movie_Ticket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {
    private String userId;
    private List<SeatDTO> showTimeSeats;
    private List<FoodDTO> foods;
    private String showTimeId;
    @Getter
    public static class SeatDTO{
        private String seatId;
        //private String showTimeId;
        private int price;
    }

    @Getter
    public static class FoodDTO{
        private String foodId;
        private int quantity;
        private int price;
    }
}
