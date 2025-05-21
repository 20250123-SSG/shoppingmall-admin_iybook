package com.iybook.sales.util;

import com.iybook.sales.constants.OrderStatus;
import com.iybook.sales.dto.request.OrderRequestFilterDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderRequestInitFilterFactory {

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

    public static OrderRequestFilterDto initCancelControl() {
        LocalDate today = LocalDate.now();

        return new OrderRequestFilterDto(
                "",
                "",
                List.of(OrderStatus.CANCEL_REQUESTED.getValue()),
                today.format(DATE_FORMATTER),
                today.format(DATE_FORMATTER)
        );
    }

}
