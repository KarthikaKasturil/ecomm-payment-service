package com.secor.ecommpaymentservice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Order
{
    private String orderId;

    private String customerId;
    private String productId;
    private Integer quantity;

    private LocalDateTime orderDate;
    private String status; // PENDING_PAYMENT, CONFIRMED, COMPLETED, CANCELLED
    private BigDecimal totalAmount;
    private LocalDateTime updatedAt;
}