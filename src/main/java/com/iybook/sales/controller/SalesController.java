package com.iybook.sales.controller;

import com.iybook.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/sales")
@Controller
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/salesList.page")
    public void salesList(){}

}
