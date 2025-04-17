package com.dang.Movie_Ticket.repository;

import com.dang.Movie_Ticket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllOrderByUserId(String userId);
}
