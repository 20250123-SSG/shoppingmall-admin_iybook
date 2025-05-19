package com.iybook.statistics.service;

import com.iybook.statistics.dao.StatisticsMapper;
import com.iybook.statistics.dto.StatisticsSalesDto;
import com.iybook.statistics.dto.StatisticsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;


@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public List<StatisticsSalesDto> getStatisticsList(StatisticsRequestDto req) {

        StatisticsMapper mapper = sqlSession.getMapper(StatisticsMapper.class);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", req.getStartDate());
        paramMap.put("endDate", req.getEndDate());

        switch (req.getGranularity()) {
            case "DAY":
                return mapper.selectDailyStatistics(paramMap);
            case "MONTH":
                return mapper.selectMonthlyStatistics(paramMap);
            case "YEAR":
                return mapper.selectYearlyStatistics(paramMap);
            default:
                throw new IllegalArgumentException("지원하지 않는 단위입니다: " + req.getGranularity());
        }
    }
}
