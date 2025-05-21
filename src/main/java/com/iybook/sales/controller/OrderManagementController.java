package com.iybook.sales.controller;

import com.iybook.sales.dto.response.OrderListResponseDto;
import com.iybook.sales.dto.request.OrderRequestFilterDto;
import com.iybook.sales.service.SalesService;
import com.iybook.sales.util.OrderRequestInitFilterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            searchFilter = OrderRequestInitFilterFactory.initOrderControl();
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
    public String acceptOrders(@RequestParam String selectedOrderIds, RedirectAttributes redirectAttributes) {
        List<String> orderIdList = Arrays.asList(selectedOrderIds.split(","));

        Map<String, List<String>> result = salesService.acceptOrders(orderIdList);

        if(result.get("fail").isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    String.format("총 %,d건 주문을 수락하였습니다.", result.get("success").size()));
        }else {
            redirectAttributes.addFlashAttribute("message",
                    String.format("%s의 주문의 상태를 변경할 수 없습니다.", String.join(",", result.get("fail"))));
        }
        return "redirect:/sales/orderControl.page";
    }

    @PostMapping("/cancelOrders.do")
    public String cancelOrders(@RequestParam String selectedOrderIds, RedirectAttributes redirectAttributes) {
        List<String> orderIdList = Arrays.asList(selectedOrderIds.split(","));

        Map<String, List<String>> result = salesService.cancelOrders(orderIdList);

        if(result.get("fail").isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    String.format("총 %,d건 주문을 취소하였습니다.", result.get("success").size()));
        }else {
            redirectAttributes.addFlashAttribute("message",
                    String.format("%s의 주문의 상태를 변경할 수 없습니다.", String.join(",", result.get("fail"))));
        }
        return "redirect:/sales/orderControl.page";
    }

}
