package com.iybook.settlement.service;

import com.iybook.settlement.dao.SettlementMapper;
import com.iybook.settlement.dto.SettlementDto;
import com.iybook.settlement.dto.SettlementStatsDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class SettlementServiceImpl implements SettlementService {

    private static final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);
    private final SqlSessionTemplate sqlSession;

    public SettlementServiceImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<SettlementDto> getSettlementByMonth(String month) {
            List<SettlementDto> settlementByStDate = sqlSession.getMapper(SettlementMapper.class).findSettlementByStDate(month);
            return settlementByStDate;
    }

    @Override
    public List<SettlementDto> getAllSettlement() {
        List<SettlementDto> list = sqlSession.getMapper(SettlementMapper.class).findAllSettlement();
        return list;
    }

    @Override
    public int updateSettlementStatus(SettlementDto settlementDto) {
        int result = sqlSession.getMapper(SettlementMapper.class).updateSettlementStatus(settlementDto);
        return result;
    }
    @Override
    public SettlementStatsDto getSettlementStats() {
        return sqlSession.getMapper(SettlementMapper.class).getSettlementStats();
    }
}
