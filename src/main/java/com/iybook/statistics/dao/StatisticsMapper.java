package com.iybook.statistics.dao;

import com.iybook.statistics.dto.StatisticsSalesDto;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    List<StatisticsSalesDto> selectDailyStatistics(Map<String, Object> param);
    List<StatisticsSalesDto> selectMonthlyStatistics(Map<String, Object> param);
    List<StatisticsSalesDto> selectYearlyStatistics(Map<String, Object> param);
}
