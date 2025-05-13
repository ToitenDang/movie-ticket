package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.OrderCreationRequest;
import com.dang.Movie_Ticket.dto.response.OrderResponse;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.entity.*;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.mapper.OrderDetailMapper;
import com.dang.Movie_Ticket.mapper.OrderMapper;
import com.dang.Movie_Ticket.mapper.ShowTimeMapper;
import com.dang.Movie_Ticket.mapper.UserMapper;
import com.dang.Movie_Ticket.repository.OrderRepository;
import com.dang.Movie_Ticket.service.*;
import com.dang.Movie_Ticket.util.enums.OrderStatus;
import com.dang.Movie_Ticket.util.enums.ProductType;
import com.dang.Movie_Ticket.util.enums.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final FoodService foodService;
    private final ShowTimeService showTimeService;
    private final ShowTimeSeatService showTimeSeatService;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, FoodService foodService,
                            ShowTimeService showTimeService, ShowTimeSeatService showTimeSeatService,
                            OrderMapper orderMapper, OrderDetailMapper orderDetailMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.foodService = foodService;
        this.showTimeService = showTimeService;
        this.showTimeSeatService = showTimeSeatService;
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public String createOrder(OrderCreationRequest request) {
        if (!showTimeService.existById(request.getShowTimeId()))
            throw new AppException((ErrorCode.SHOWTIME_NOT_EXISTED));
        User user = this.userService.getUserById(request.getUserId());
        ShowTime showTime = this.showTimeService.getShowTimeById(request.getShowTimeId());
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setShowTime(showTime);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderCreationRequest.SeatDTO seat : request.getShowTimeSeats()) {
            ShowTimeSeat showTimeSeat = this.showTimeSeatService
                    .findByShowTimeIdAndSeatId(request.getShowTimeId(), seat.getSeatId());
            if (!showTimeSeat.getStatus().equals(SeatStatus.RESERVED))
                throw new AppException(ErrorCode.ORDER_SEAT_INVALID);
//            showTimeSeat.setStatus(SeatStatus.RESERVED);
//            showTimeSeatRepository.save(showTimeSeat);

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productId(seat.getSeatId())
                    .productType(ProductType.SEAT)
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

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .productId(food.getFoodId())
                    .quantity(food.getQuantity())
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
    public OrderResponse getOrder(String orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
//        return OrderResponse.builder()
//                .id(order.getId())
//                .userId(order.getUser().getId())
//                .totalPrice(order.getTotalPrice())
//                .status(order.getStatus().toString())
//                .showTimeId(order.getShowTime().getId())
//                .orderDetails(order.getOrderDetails().stream()
//                        .map(orderDetailMapper::toOrderDetailResponse)
//                        .collect(Collectors.toList()))
//                .createdAt(order.getCreateAt())
//                .build();
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public PageResponse<?> getOrders(int pageNo, int pageSize, String sortBy) {
        int p = 0;
        if (pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        // Nếu có điều kiện để sắp xếp sortBy
        if (StringUtils.hasLength(sortBy)) {
            // Một điều kiện có 3 phần là (key)(:)(asc hoặc desc) và dùng với bất kỳ thuộc tính nào
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(sorts));
        Page<Order> orders = this.orderRepository.findAll(pageable);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(orders.getTotalPages())
                .items(orders.stream().map(order -> OrderResponse.builder()
                                .id(order.getId())
                                .totalPrice(order.getTotalPrice())
                                .userId(order.getUser().getId())
                                .showTimeId(order.getShowTime().getId())
                                .status(order.getStatus().toString())
                                .createAt(order.getCreateAt())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(String userId) {

        List<Order> orders = this.orderRepository.findAllOrderByUserId(userId);
        return orders.stream().map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .totalPrice(order.getTotalPrice())
                        .userId(order.getUser().getId())
                        .showTimeId(order.getShowTime().getId())
                        .status(order.getStatus().toString())
                        .createAt(order.getCreateAt())
                        .build())
                .toList();
    }

    @Override
    public void deleteOrder(String orderId) {
        this.orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public void changeOrderStatus(String orderId, OrderStatus status) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        order.setStatus(status);
        String showTimeId = order.getShowTime().getId();
        if (status.equals(OrderStatus.PAID)) {
            List<String> seatIds = order.getOrderDetails().stream()
                    .filter(orderDetail -> orderDetail.getProductType().equals(ProductType.SEAT))
                    .map(OrderDetail::getProductId)
                    .collect(Collectors.toList());

            if (!seatIds.isEmpty()) {
                showTimeSeatService.batchUpdateSeatStatus(showTimeId, seatIds, SeatStatus.BOOKED);
            }
        }

        this.orderRepository.save(order);
    }

}
