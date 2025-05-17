package com.iybook.sales.controller;

import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sales")
@Controller
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/salesList.page")
    public String salesList(){
        return "sales/salesList";
    }

    @PostMapping("/search.do")
    public String search(@ModelAttribute OrderRequestFilterDto filter){
        log.debug("startDate: {}", filter.getStartDate());
        log.debug("endDate: {}", filter.getEndDate());
        log.debug("customerId: {}", filter.getCustomerId());
        log.debug("orderId: {}", filter.getOrderId());
        log.debug("orderStatus: {}", filter.getOrderStatus());

        //todo
        /**
         * 입력되지 않은 경우는 빈칸트로 들어감.
         * 동적쿼리로 필터링 하기 위해서는 null을 다루는 것이 유리할 거 같은데 빈칸을 어떻게 처리할지 고민
         *
         * 리스트를 띄우는 것도 동적이 나을까?
         * 검색 조건을 submit 하게되면 조건이 초기화가 되어버려서 사용자가 현재 무슨 조건의 결과인지 확인 할 수 없다.
         */

        return "redirect:/sales/salesList.page";
    }


}
