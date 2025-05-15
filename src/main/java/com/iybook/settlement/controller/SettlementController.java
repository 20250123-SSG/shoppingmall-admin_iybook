package com.iybook.settlement.controller;

import com.iybook.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/settlement")
@Controller
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/settleHome.page")
    public void settleHome(){}

}
