package com.iybook.sales.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequestFilterDto {

    private String orderId;
    private String customerId;
    private List<String> orderStatus; ///동적쿼리 order_status IN <foreach>
    private String startDate;
    private String endDate;

}
