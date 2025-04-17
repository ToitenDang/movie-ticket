package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.OrderCreationRequest;
import com.dang.Movie_Ticket.entity.*;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.repository.OrderRepository;
import com.dang.Movie_Ticket.repository.ShowTimeRepository;
import com.dang.Movie_Ticket.repository.ShowTimeSeatRepository;
import com.dang.Movie_Ticket.service.FoodService;
import com.dang.Movie_Ticket.service.OrderService;
import com.dang.Movie_Ticket.util.enums.OrderStatus;
import com.dang.Movie_Ticket.util.enums.ProductType;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final ShowTimeRepository showTimeRepository;
    private final ShowTimeSeatRepository showTimeSeatRepository;

    public OrderServiceImpl(OrderRepository orderRepository, FoodService foodService, ShowTimeRepository showTimeRepository, ShowTimeSeatRepository showTimeSeatRepository) {
        this.orderRepository = orderRepository;
        this.foodService = foodService;
        this.showTimeRepository = showTimeRepository;
        this.showTimeSeatRepository = showTimeSeatRepository;
    }

    @Override
    public String createOrder(OrderCreationRequest request) {
        ShowTime showTime = this.showTimeRepository.findById(request.getShowTimeId())
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_EXISTED));
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PENDING);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderCreationRequest.SeatDTO seat : request.getShowTimeSeats()) {
            ShowTimeSeat showTimeSeat = this.showTimeSeatRepository
                    .findSeatByShowTimeIdAndSeatId(request.getShowTimeId(), seat.getSeatId());
            if (!showTimeSeat.getSeat().getStatus().equals(SeatStatus.EMPTY))
                throw new AppException(ErrorCode.ORDER_SEAT_INVALID);
            showTimeSeat.setStatus(SeatStatus.RESERVED);
            showTimeSeatRepository.save(showTimeSeat);

//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//            orderDetail.setProductId(seat.getSeatId());
//            orderDetail.setPrice(seat.getPrice());
//            orderDetail.setProductType(ProductType.SEAT);
//            orderDetails.add(orderDetail);

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productId(seat.getSeatId())
                    .productType(ProductType.SEAT)
                    .showTimeId(request.getShowTimeId())
                    .quantity(1)
                    .price(seat.getPrice())
                    .build();
            orderDetails.add(orderDetail);
        }

        for (OrderCreationRequest.FoodDTO food : request.getFoods()) {
            Food foodItem = this.foodService.getFood(food.getFoodId());
//            if (foodItem.getQuantity() < food.getQuantity())
//                throw new AppException(ErrorCode.FOOD_QUANTITY_INVALID);
//
            this.foodService.updateQuantityAndSold(food.getFoodId(), food.getQuantity());

//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//            orderDetail.setProductId(food.getFoodId());
//            orderDetail.setQuantity(food.getQuantity());
//            orderDetail.setPrice(food.getPrice());
//            orderDetail.setProductType(ProductType.FOOD);
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productId(food.getFoodId())
                    .quantity(food.getQuantity())
                    .showTimeId(request.getShowTimeId())
                    .price(food.getPrice())
                    .productType(ProductType.FOOD)
                    .build();

            orderDetails.add(orderDetail);

        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(orderDetails.stream().mapToInt(od -> od.getPrice() * od.getQuantity()).sum());
        this.orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Order getOrder(String orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
    }

    @Override
    public List<Order> getOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return this.orderRepository.findAllOrderByUserId(userId);
    }

    @Override
    public void deleteOrder(String orderId) {
        this.orderRepository.deleteById(orderId);
    }

    @Override
    public void changeOrderStatus(String orderId, OrderStatus status) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        order.setStatus(status);
        this.orderRepository.save(order);
    }
}
