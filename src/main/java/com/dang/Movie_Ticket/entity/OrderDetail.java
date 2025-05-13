package com.dang.Movie_Ticket.entity;

import com.dang.Movie_Ticket.util.enums.FoodStatus;
import com.dang.Movie_Ticket.util.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderdetail")
public class OrderDetail extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;


}
