package com.iybook.sales.controller;

import com.iybook.sales.dto.response.OrderDetailResponseDto;
import com.iybook.sales.dto.response.OrderListResponseDto;
import com.iybook.sales.dto.request.OrderRequestFilterDto;
import com.iybook.sales.dto.response.OrderResponseDto;
import com.iybook.sales.service.SalesService;
import com.iybook.sales.util.OrderRequestInitFilterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            searchFilter = OrderRequestInitFilterFactory.initSalesList();
            orderListResult = OrderListResponseDto.empty();
        }else {
            orderListResult = salesService.getOrderListAndPageInfoByFilter(page, searchFilter);
        }
        model.addAttribute("filter", searchFilter);
        model.addAttribute("orderListResult", orderListResult);
        model.addAttribute("orderCount", orderListResult.getTotalOrderCount());

        return "sales/salesList";
    }

    @ResponseBody
    @GetMapping("/orderDetail.page")
    public OrderResponseDto orderDetail(String orderId) {
        return salesService.getOrderDetailByOrderId(Integer.parseInt(orderId));
    }

}
