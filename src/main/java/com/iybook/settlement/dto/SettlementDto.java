package com.iybook.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementDto {

    private int stId; // 정산 ID
    private int orderId; // 주문 ID
    private LocalDate stDate; // 정산기준일
    private LocalDate exDate; // 정산예정일
    private LocalDate compDate; // 정산완료일
    private int stPrice; // 정산금
    private String stStatus; // 정산상태 (VARCHAR(15))
    private int tax; // 결제수수료
}
