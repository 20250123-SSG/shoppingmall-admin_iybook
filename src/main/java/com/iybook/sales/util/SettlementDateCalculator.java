package com.iybook.sales.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SettlementDateCalculator {

    private final static int PLUS_MONTH = 1;
    private final static int START_DAY_OF_MONTH = 1;

    public LocalDate calculate(LocalDate orderDate) {
        return orderDate.plusMonths(PLUS_MONTH).withDayOfMonth(START_DAY_OF_MONTH);
    }

}
