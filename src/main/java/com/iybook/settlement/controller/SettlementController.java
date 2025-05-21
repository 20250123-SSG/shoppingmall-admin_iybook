package com.iybook.settlement.controller;

import com.iybook.settlement.dto.SettlementDto;
import com.iybook.settlement.dto.SettlementStatsDto;
import com.iybook.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/settlement")
@Controller
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/settleHome.page")
    public void settleHome(){}

    @GetMapping("/monthly")
    @ResponseBody // 이 어노테이션을 추가하여 JSON 데이터를 반환하도록 변경
    public List<SettlementDto> settleMonthPage(String month){
        List<SettlementDto> list = settlementService.getSettlementByMonth(month);
        return list;
    }

    @GetMapping("/settlementList")
    @ResponseBody
    public List<SettlementDto> settleList() {
        List<SettlementDto> list = settlementService.getAllSettlement();
        return list;
    }

    @GetMapping("/main/settlement-stats")
    @ResponseBody
    public SettlementStatsDto bookStats() {
        return settlementService.getSettlementStats();
    }
}
