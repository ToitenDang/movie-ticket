package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.OrderCreationRequest;
import com.dang.Movie_Ticket.dto.response.OrderResponse;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.entity.Order;
import com.dang.Movie_Ticket.util.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    String createOrder(OrderCreationRequest request);
    OrderResponse getOrder(String orderId);
    PageResponse<?> getOrders(int pageNo, int pageSize, String sortBy);
    List<OrderResponse> getOrdersByUserId(String userId);
    void deleteOrder(String orderId);
    void changeOrderStatus(String orderId, OrderStatus status);
}
