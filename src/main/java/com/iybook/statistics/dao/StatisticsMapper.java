package com.iybook.statistics.dao;

import com.iybook.statistics.dto.StatisticsCategoryDto;
import com.iybook.statistics.dto.StatisticsRequestDto;
import com.iybook.statistics.dto.StatisticsSalesDto;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    List<StatisticsSalesDto> selectDailyStatistics(StatisticsRequestDto req);
    List<StatisticsSalesDto> selectMonthlyStatistics(StatisticsRequestDto req);
    List<StatisticsSalesDto> selectYearlyStatistics(StatisticsRequestDto req);

    // 카테고리
    List<StatisticsCategoryDto> selectCategoryAllStatistics(StatisticsRequestDto req);
    List<StatisticsCategoryDto> selectCategoryGenderStatistics(StatisticsRequestDto req);
    List<StatisticsCategoryDto> selectCategoryAgeStatistics(StatisticsRequestDto req);

}
