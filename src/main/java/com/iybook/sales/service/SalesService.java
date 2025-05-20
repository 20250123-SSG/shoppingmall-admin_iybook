package com.iybook.sales.service;

import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;

import java.util.List;
import java.util.Map;

public interface SalesService {

    OrderListResponseDto getOrderListAndPageInfoByFilter(int page, OrderRequestFilterDto filter);

    Map<String, List<String>> acceptOrders(List<String> orderIdList);

    Map<String, List<String>> cancelOrders(List<String> orderIdList);


}
