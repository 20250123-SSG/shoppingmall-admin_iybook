package com.iybook.settlement.service;

import com.iybook.settlement.dto.SettlementDto;

import java.util.List;

public interface SettlementService {
    List<SettlementDto> getSettlementByMonth(String month);
    List<SettlementDto> getAllSettlement();
    int updateSettlementStatus(SettlementDto settlementDto);
}
