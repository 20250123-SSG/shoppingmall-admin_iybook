package com.iybook.sales.dao;

import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.OrderRequestFilterDto;

import java.util.List;

public interface SalesMapper {

    int selectOrderListCountByFilter(OrderRequestFilterDto filter);

    List<OrderDto> selectOrderListByFilterWithPaging(SingleOrderPagingRequestDto pagingInfo);

}
