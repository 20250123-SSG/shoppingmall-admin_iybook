package com.iybook.sales.controller;

import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sales")
@Controller
public class OrderManagementController {

    private final SalesService salesService;

    @GetMapping("/orderControl.page")
    public String orderControlPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @ModelAttribute OrderRequestFilterDto searchFilter,
                                   Model model){
        OrderListResponseDto orderListResult;

        boolean isFirstPageLoad = searchFilter.getStartDate() == null || searchFilter.getEndDate() == null;
        if(isFirstPageLoad){
            searchFilter = OrderRequestFilterDto.initSalesList();
            orderListResult = OrderListResponseDto.empty();
        }else {
            orderListResult = salesService.getOrderListAndPageInfoByFilter(page, searchFilter);
            log.debug("*******************************************************");
            log.debug("now page = {}",page);
            log.debug("startDate: {}", searchFilter.getStartDate());
            log.debug("endDate: {}", searchFilter.getEndDate());
            log.debug("customerId: {}", searchFilter.getCustomerId());
            log.debug("orderId: {}", searchFilter.getOrderId());
            log.debug("orderStatus: {}", searchFilter.getOrderStatus());
            log.debug("*******************************************************");
        }
        model.addAttribute("filter", searchFilter);
        model.addAttribute("orderListResult", orderListResult);
        model.addAttribute("orderCount", orderListResult.getTotalOrderCount());

        return "sales/orderControl";
    }

    @PostMapping("/acceptOrders.do")
    public String acceptOrders(@RequestParam String selectedOrderIds) {
        log.debug(selectedOrderIds);
        List<String> orderIdList = Arrays.asList(selectedOrderIds.split(","));
        log.debug("orderIdList = {}",orderIdList);

        /// orderIdList로 배송완료로 상태 변경
        return "redirect:/sales/salesList.page";
    }

    @PostMapping("/cancelOrders.do")
    public String cancelOrders(@RequestParam String selectedOrderIds) {
        List<String> orderIdList = Arrays.asList(selectedOrderIds.split(","));
        log.debug("orderIdList = {}",orderIdList);

        /// orderIdList로 취소완료로 상태 변경
        return "redirect:/sales/salesList.page";
    }

}
