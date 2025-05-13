package com.dang.Movie_Ticket.controller;

import com.dang.Movie_Ticket.dto.request.OrderCreationRequest;
import com.dang.Movie_Ticket.dto.request.ResponseData;
import com.dang.Movie_Ticket.service.OrderService;
import com.dang.Movie_Ticket.util.enums.OrderStatus;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseData<?> createOrder(@RequestBody OrderCreationRequest request){
        log.info("Create order");
        return new ResponseData<>(HttpStatus.CREATED.value(), "Create Order succeed",
                this.orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseData<?> getOrder(@PathVariable String orderId){
        log.info("Get order by id: {}", orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Order", this.orderService.getOrder(orderId));
    }

    @GetMapping
    public ResponseData<?> getOrders(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                     @Min(1) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                     @RequestParam(required = false) String sortBy){
        log.info("Get all order");
        return new ResponseData<>(HttpStatus.OK.value(), "List orders",
                this.orderService.getOrders(pageNo, pageSize, sortBy));
    }

    @GetMapping("/user-order/{userId}")
    public ResponseData<?> getOrdersByUserId(@PathVariable String userId){
        log.info("Get all order of user: {}", userId);
        return new ResponseData<>(HttpStatus.OK.value(), "List orders of user",
                    this.orderService.getOrdersByUserId(userId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseData<?> deleteOrder(@PathVariable String orderId){
        this.orderService.deleteOrder(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete Order succeed");

    }

    @PatchMapping("/{orderId}")
    public ResponseData<?> changeOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status){
        this.orderService.changeOrderStatus(orderId, status);
        log.info("Change order status");
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Change order status succeed");
    }

}
