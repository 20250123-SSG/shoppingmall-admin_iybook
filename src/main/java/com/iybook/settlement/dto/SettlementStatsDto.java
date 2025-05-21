package com.iybook.settlement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettlementStatsDto {
    private int confirmedPurchaseCount;  // 구매확정 갯수
    private int expectedSettlementAmount; // 정산예정 금액
    private int sumTax;
}
