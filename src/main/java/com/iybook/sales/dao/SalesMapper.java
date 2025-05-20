package com.iybook.sales.dao;

import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.request.OrderStatusUpdateDto;
import com.iybook.sales.dto.request.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.request.OrderRequestFilterDto;

import java.util.List;

public interface SalesMapper {

    int selectOrderListCountByFilter(OrderRequestFilterDto filter);

    List<OrderDto> selectOrderListByFilterWithPaging(SingleOrderPagingRequestDto pagingInfo);

    int updateOrderStatusByOrderId(OrderStatusUpdateDto orderStatusChange);

    List<OrderDto> selectOrderListByIdForChangeStatus(List<String> orderIdList);

}
