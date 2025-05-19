package com.iybook.sales.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private String orderId;
    private String customerId;
    private String orderStatus;
    private int orderTotalCount;
    private int orderTotalPrice;
    private String payment;
    private String orderMemo;
    private String orderDate;

}