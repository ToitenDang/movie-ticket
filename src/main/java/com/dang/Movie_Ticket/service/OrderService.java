package com.dang.Movie_Ticket.service;

import com.dang.Movie_Ticket.dto.request.OrderCreationRequest;
import com.dang.Movie_Ticket.entity.Order;
import com.dang.Movie_Ticket.util.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    String createOrder(OrderCreationRequest request);
    Order getOrder(String orderId);
    List<Order> getOrders();
    List<Order> getOrdersByUserId(String userId);
    void deleteOrder(String orderId);
    void changeOrderStatus(String orderId, OrderStatus status);
}
