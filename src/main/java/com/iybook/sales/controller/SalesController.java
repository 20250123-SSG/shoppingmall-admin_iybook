package com.iybook.sales.controller;

import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sales")
@Controller
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/salesList.page")
    public String salesList(@RequestParam(value = "page", defaultValue = "1") int page,
                            @ModelAttribute OrderRequestFilterDto searchFilter,
                            Model model){
        OrderListResponseDto orderListResult;

        boolean isFirstPageLoad = searchFilter.getStartDate() == null || searchFilter.getEndDate() == null;
        if(isFirstPageLoad){
            searchFilter = OrderRequestFilterDto.initSalesList();
            orderListResult = OrderListResponseDto.empty();
        }else {
            orderListResult = salesService.getOrderListAndPageInfoByFilter(page, searchFilter);
        }
        model.addAttribute("filter", searchFilter);
        model.addAttribute("orderListResult", orderListResult);
        model.addAttribute("orderCount", orderListResult.getTotalOrderCount());

        return "sales/salesList";
    }

}
