package com.iybook.sales.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderStatsResponseDto {
    private int orderCompleted;  // 주문완료
    private int cancelRequested;
}
