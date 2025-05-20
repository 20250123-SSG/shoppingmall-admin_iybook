package com.iybook.statistics.service;

import com.iybook.statistics.dto.StatisticsCategoryDto;
import com.iybook.statistics.dto.StatisticsSalesDto;
import com.iybook.statistics.dto.StatisticsRequestDto;

import java.util.List;

public interface StatisticsService {
    List<StatisticsSalesDto> getStatisticsList(StatisticsRequestDto req);
    // 카테고리
    List<StatisticsCategoryDto> getCategoryAllStatistics(StatisticsRequestDto req);
    List<StatisticsCategoryDto> getCategoryGenderStatistics(StatisticsRequestDto req);
    List<StatisticsCategoryDto> getCategoryAgeStatistics(StatisticsRequestDto req);

}