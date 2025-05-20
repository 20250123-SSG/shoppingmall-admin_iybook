package com.iybook.sales.dto;

import com.iybook.sales.constants.OrderStatus;
import com.iybook.sales.constants.OrderTableInfo;
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
    private List<String> orderStatus; ///동적쿼리 order_status IN <foreach>
    private String startDate;
    private String endDate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static OrderRequestFilterDto initSalesList() {
        LocalDate today = LocalDate.now();

        return new OrderRequestFilterDto(
                "",
                "",
                List.of(OrderStatus.COMPLETED.getValue(),
                        OrderStatus.CANCEL_REQUESTED.getValue(),
                        OrderStatus.CANCELED.getValue(),
                        OrderStatus.DELIVERED.getValue()),
                today.minusDays(7).format(DATE_FORMATTER),
                today.format(DATE_FORMATTER)
        );
    }

    public static OrderRequestFilterDto initOrderControl() {
        LocalDate today = LocalDate.now();

        return new OrderRequestFilterDto(
                "",
                "",
                List.of(OrderStatus.COMPLETED.getValue()),
                today.format(DATE_FORMATTER),
                today.format(DATE_FORMATTER)
        );
    }

}
