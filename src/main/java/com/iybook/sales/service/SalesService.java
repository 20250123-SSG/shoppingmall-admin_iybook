package com.iybook.sales.service;

import com.iybook.sales.dto.response.OrderListResponseDto;
import com.iybook.sales.dto.request.OrderRequestFilterDto;
import com.iybook.sales.dto.response.OrderResponseDto;
import com.iybook.sales.dto.response.OrderStatsResponseDto;

import java.util.List;
import java.util.Map;

public interface SalesService {

    OrderListResponseDto getOrderListAndPageInfoByFilter(int page, OrderRequestFilterDto filter);

    OrderResponseDto getOrderDetailByOrderId(int orderId);

    Map<String, List<String>> acceptOrders(List<String> orderIdList);

    Map<String, List<String>> cancelOrders(List<String> orderIdList);

    Map<String, List<String>> approveCancelOrders(List<String> orderIdList);

    Map<String, List<String>> rejectCancelOrders(List<String> orderIdList);

    OrderStatsResponseDto getOrderStats();
}
