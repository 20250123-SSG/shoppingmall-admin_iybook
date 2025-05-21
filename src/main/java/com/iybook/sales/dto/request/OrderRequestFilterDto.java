package com.iybook.sales.dto.request;

import com.iybook.sales.constants.OrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderRequestFilterDto {

    private String orderId;
    private String customerId;
    private List<String> orderStatus;
    private String startDate;
    private String endDate;

}
