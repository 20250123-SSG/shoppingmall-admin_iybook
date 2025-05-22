package com.iybook.statistics.service;

import com.iybook.statistics.dao.StatisticsMapper;
import com.iybook.statistics.dto.StatisticsCategoryDto;
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

        switch (req.getGranularity()) {
            case "DAY":
                return mapper.selectDailyStatistics(req);
            case "MONTH":
                return mapper.selectMonthlyStatistics(req);
            case "YEAR":
                return mapper.selectYearlyStatistics(req);
            default:
                throw new IllegalArgumentException("지원하지 않는 단위입니다: " + req.getGranularity());
        }
    }


    @Override
    public List<StatisticsCategoryDto> getCategoryAllStatistics(StatisticsRequestDto req) {
        return sqlSession.getMapper(StatisticsMapper.class).selectCategoryAllStatistics(req);
    }

    @Override
    public List<StatisticsCategoryDto> getCategoryGenderStatistics(StatisticsRequestDto req) {
        return sqlSession.getMapper(StatisticsMapper.class).selectCategoryGenderStatistics(req);
    }

    @Override
    public List<StatisticsCategoryDto> getCategoryAgeStatistics(StatisticsRequestDto req) {
        return sqlSession.getMapper(StatisticsMapper.class).selectCategoryAgeStatistics(req);
    }
}
