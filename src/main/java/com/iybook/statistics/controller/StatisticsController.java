package com.iybook.statistics.controller;

import com.iybook.statistics.dto.StatisticsSalesDto;
import com.iybook.statistics.dto.StatisticsRequestDto;
import com.iybook.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/statistics")
@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;


    @GetMapping("/stats.page")
    public String showStatsPage(Model model) {
        // 기본값 설정 (예: 오늘 날짜)
        model.addAttribute("summaryList", Collections.emptyList());
        model.addAttribute("totalSales", 0);
        model.addAttribute("totalOrderCount", 0);
        model.addAttribute("totalCancelCount", 0);
        model.addAttribute("granularity", "DAY");
        return "statistics/stats";  // = /WEB-INF/views/statistics/stats.jsp
    }


    @GetMapping("/summary.do")
    public String getStatisticsSummary(StatisticsRequestDto req, Model model) {
        List<StatisticsSalesDto> summaryList = statisticsService.getStatisticsList(req);
        System.out.println("📊 [로그] 받은 데이터 개수: " + summaryList.size());
        summaryList.forEach(s -> System.out.println("👉 날짜: " + s.getStatisticsDate() + ", 매출: " + s.getTotalSales()));


        int totalSales = summaryList.stream().mapToInt(StatisticsSalesDto::getTotalSales).sum();
        int totalOrderCount = summaryList.stream().mapToInt(StatisticsSalesDto::getOrderCount).sum();
        int totalCancelCount = summaryList.stream().mapToInt(StatisticsSalesDto::getCancelCount).sum();

        model.addAttribute("summaryList", summaryList);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrderCount", totalOrderCount);
        model.addAttribute("totalCancelCount", totalCancelCount);
        model.addAttribute("granularity", req.getGranularity());

        return "statistics/stats";
    }

    @GetMapping("/detail.do")
    @ResponseBody
    public List<StatisticsSalesDto> getDetailStatistics(StatisticsRequestDto req) {
        if ("MONTH".equals(req.getGranularity())) {
            req.setStartDate(req.getStartDate() + "-01");
            req.setEndDate(req.getStartDate().substring(0, 7) + "-31");
            req.setGranularity("DAY");
        } else if ("YEAR".equals(req.getGranularity())) {
            req.setStartDate(req.getStartDate() + "-01-01");
            req.setEndDate(req.getStartDate().substring(0, 4) + "-12-31");
            req.setGranularity("MONTH");
        } else {
            return Collections.emptyList();
        }
        return statisticsService.getStatisticsList(req);
    }
}
