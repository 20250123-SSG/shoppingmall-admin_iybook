package com.iybook.sales.dto.response;

import com.iybook.sales.dto.CustomerDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderResponseDto {

    private String orderId;
    private CustomerDto customer;
    private int orderTotalCount;
    private int orderTotalPrice;
    private String payment;
    private String orderStatus;
    private LocalDateTime orderDate;

    private List<OrderDetailResponseDto> orderDetailList;

}
