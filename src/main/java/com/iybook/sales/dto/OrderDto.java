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
    private LocalDateTime updateDate;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getFormattedOrderDate() {
        return orderDate != null
                ? orderDate.format(formatter)
                : "";
    }

    public String getFormattedUpdateDate() {
        return updateDate != null
                ? updateDate.format(formatter)
                : "";
    }

}