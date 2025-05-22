package com.iybook.statistics.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class StatisticsSalesDto {
    private String statisticsDate;
    private int totalSales;
    private int orderCount;
    private int cancelCount;
}
