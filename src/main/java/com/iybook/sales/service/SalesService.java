package com.iybook.sales.service;

import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;

public interface SalesService {

    OrderListResponseDto getOrderListAndPageInfoByFilter(int page, OrderRequestFilterDto filter);

}
