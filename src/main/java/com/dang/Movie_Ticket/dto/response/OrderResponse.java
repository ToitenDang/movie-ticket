package com.dang.Movie_Ticket.dto.response;

import com.dang.Movie_Ticket.entity.OrderDetail;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private int totalPrice;
    private String status;
    private String showTimeId;
    private String userId;
    private List<OrderDetailResponse> orderDetails;
    private Date createAt;
}
