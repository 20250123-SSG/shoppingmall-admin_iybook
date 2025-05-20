package com.iybook.statistics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StatisticsRequestDto {
    private String startDate;
    private String endDate;
    private String granularity;
}
