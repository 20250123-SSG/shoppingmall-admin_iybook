package com.iybook.statistics.dao;

import com.iybook.statistics.dto.StatisticsCategoryDto;
import com.iybook.statistics.dto.StatisticsSalesDto;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    List<StatisticsSalesDto> selectDailyStatistics(Map<String, Object> param);
    List<StatisticsSalesDto> selectMonthlyStatistics(Map<String, Object> param);
    List<StatisticsSalesDto> selectYearlyStatistics(Map<String, Object> param);
    // 카테고리
    List<StatisticsCategoryDto> selectCategoryAllStatistics(Map<String, Object> paramMap);
    List<StatisticsCategoryDto> selectCategoryGenderStatistics(Map<String, Object> paramMap);
    List<StatisticsCategoryDto> selectCategoryAgeStatistics(Map<String, Object> paramMap);

}
