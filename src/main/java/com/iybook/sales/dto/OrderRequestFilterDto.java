package com.iybook.sales.dto;

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

    private static final List<String> DEFAULT_ORDER_STATUS =
            List.of("주문완료", "취소요청", "취소완료", "배송완료");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static OrderRequestFilterDto init() {
        LocalDate today = LocalDate.now();

        return new OrderRequestFilterDto(
                "",
                "",
                DEFAULT_ORDER_STATUS,
                today.minusDays(7).format(DATE_FORMATTER),
                today.format(DATE_FORMATTER)
        );
    }

}
