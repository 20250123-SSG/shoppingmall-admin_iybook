package com.iybook.sales.dto.response;

import com.iybook.sales.dto.CustomerDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderResponseDto {

    private String orderId;
    private CustomerDto customer;
    private int orderTotalCount;
    private int orderTotalPrice;
    private String payment;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime updateDate;
    private String orderMemo;
    private List<OrderDetailResponseDto> orderDetailList;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getOrderDate() {
        return orderDate != null
                ? orderDate.format(formatter)
                : "";
    }

    public String getUpdateDate() {
        return updateDate != null
                ? updateDate.format(formatter)
                : "";
    }

}
