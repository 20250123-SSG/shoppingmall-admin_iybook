package com.iybook.settlement.dao;

import com.iybook.settlement.dto.SettlementDto;

import java.util.List;

public interface SettlementMapper {
    List<SettlementDto> findSettlementByStDate(String month);
    List<SettlementDto> findAllSettlement();
    int insertSettlement(SettlementDto settlementDto);
    int updateSettlementStatus(SettlementDto settlementDto);
}
