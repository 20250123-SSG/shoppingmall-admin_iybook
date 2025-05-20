package com.iybook.sales.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime orderDate;

    public String getFormattedOrderDate() {
        return orderDate != null
                ? orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "";
    }

}