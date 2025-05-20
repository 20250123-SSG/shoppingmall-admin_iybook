package com.iybook.sales.dao;

import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.OrderStatusUpdateDto;
import com.iybook.sales.dto.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.OrderRequestFilterDto;

import java.util.List;

public interface SalesMapper {

    int selectOrderListCountByFilter(OrderRequestFilterDto filter);

    List<OrderDto> selectOrderListByFilterWithPaging(SingleOrderPagingRequestDto pagingInfo);

    int updateOrderStatusByOrderId(OrderStatusUpdateDto orderStatusChange);

    List<OrderDto> selectOrderListByIdForChangeStatus(List<String> orderIdList);


}
