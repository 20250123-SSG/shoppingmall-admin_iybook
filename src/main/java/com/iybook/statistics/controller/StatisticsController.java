package com.iybook.statistics.controller;

import com.iybook.statistics.dto.StatisticsSalesDto;
import com.iybook.statistics.dto.StatisticsRequestDto;
import com.iybook.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;

    // 페이지 이동
    @GetMapping("/stats.page")
    public void statsPage() {
    }

    // 통계 데이터 조회
    @GetMapping("/summary.do")
    @ResponseBody
    public List<StatisticsSalesDto> getSummary(@ModelAttribute StatisticsRequestDto req) {
        log.info("[요약 조회] {}, {}", req.getStartDate(), req.getEndDate());
        return statisticsService.getStatisticsList(req);
    }

    // 카테고리 통계 페이지
    @GetMapping("/statsCategory.page")
    public void statsCategoryPage() {

    }



}
