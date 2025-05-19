package com.iybook.sales.service;

import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.sales.dto.OrderStatusChangeResult;

import java.util.List;

public interface SalesService {

    OrderListResponseDto getOrderListAndPageInfoByFilter(int page, OrderRequestFilterDto filter);

    OrderStatusChangeResult acceptOrder(List<String> orderIdList);
}
