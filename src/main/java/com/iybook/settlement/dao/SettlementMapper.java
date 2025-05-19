package com.iybook.settlement.dao;

import com.iybook.settlement.dto.SettlementDto;

import java.util.List;

public interface SettlementMapper {
    List<SettlementDto> findSettlementByStDate();
//    List<SettlementDto>
}
