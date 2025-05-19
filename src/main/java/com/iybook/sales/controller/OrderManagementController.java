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

/**
 * 주문 통합 조회와 다른 점
 *
 * 주 행동
 * - 주문완료 -> 배송완료 or 취소완료
 *
 * 기본 orderStatus
 * - 주문 완료 + 오늘
 *
 * 필요한 orderStatus는
 * - 주문 완료
 *
 *
 */
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
            searchFilter = OrderRequestFilterDto.initOrderControl();
            orderListResult = OrderListResponseDto.empty();
        }else {
            orderListResult = salesService.getOrderListAndPageInfoByFilter(page, searchFilter);
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



        salesService.acceptOrder(orderIdList);



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
