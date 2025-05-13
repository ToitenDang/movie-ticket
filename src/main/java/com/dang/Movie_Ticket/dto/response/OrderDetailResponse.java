package com.dang.Movie_Ticket.dto.response;

import com.dang.Movie_Ticket.entity.OrderDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private String productId;
    private String productType;
    private int price;
    private int quantity;
}
