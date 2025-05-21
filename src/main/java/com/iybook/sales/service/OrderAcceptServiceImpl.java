package com.iybook.sales.service;

import com.iybook.sales.constants.SettlementStatus;
import com.iybook.sales.dao.SalesMapper;
import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.request.OrderStatusUpdateDto;
import com.iybook.sales.util.SettlementDateCalculator;
import com.iybook.sales.util.TaxCalculator;
import com.iybook.settlement.dao.SettlementMapper;
import com.iybook.settlement.dto.SettlementDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class OrderAcceptServiceImpl implements OrderAcceptService {

    private final SqlSessionTemplate sqlSession;
    private final TaxCalculator taxCalculator;
    private final SettlementDateCalculator settlementDateCalculator;

    public boolean acceptOrder(OrderStatusUpdateDto updateInto) {
        SalesMapper salesMapper = sqlSession.getMapper(SalesMapper.class);
        SettlementMapper settlementMapper = sqlSession.getMapper(SettlementMapper.class);

        int updateOrderStatusResult = salesMapper.updateOrderStatusByOrderId(updateInto);
        if (updateOrderStatusResult != 1) {
            return false;
        }

        OrderDto order = updateInto.order();
        LocalDate orderDate = LocalDate.from(order.getOrderDate());

        SettlementDto settlementDto = SettlementDto.builder()
                .orderId(Integer.parseInt(order.getOrderId()))
                .stDate(orderDate)
                .exDate(settlementDateCalculator.calculate(orderDate))
                .tax((int) taxCalculator.calculate(order.getOrderTotalPrice(), order.getPayment()))
                .stStatus(SettlementStatus.PENDING.getValue())
                .build();

        int insertSettlementResult = settlementMapper.insertSettlement(settlementDto);
        if (insertSettlementResult != 1) {
            return false;
        }

        return true;
    }

}
