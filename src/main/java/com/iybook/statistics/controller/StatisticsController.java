package com.iybook.statistics.controller;

import com.iybook.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/statistics")
@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/stats.page")
    public void stats(){}

    // 1. 매출 통계

    // 2. 주문건, 취소건 통계

    // 3. 카테고리별 통계
}
