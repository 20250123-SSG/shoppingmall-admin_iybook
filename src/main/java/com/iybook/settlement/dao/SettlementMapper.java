package com.iybook.settlement.dao;

import com.iybook.settlement.dto.SettlementDto;
import com.iybook.settlement.dto.SettlementStatsDto;

import java.util.List;

public interface SettlementMapper {
    List<SettlementDto> findSettlementByStDate(String month);
    List<SettlementDto> findAllSettlement();
    SettlementStatsDto getSettlementStats();
    int insertSettlement(SettlementDto settlementDto);
    int updateSettlementStatus(SettlementDto settlementDto);
}
