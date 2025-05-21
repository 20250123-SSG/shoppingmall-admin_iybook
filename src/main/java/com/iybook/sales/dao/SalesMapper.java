package com.iybook.sales.dao;

import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.request.OrderStatusUpdateDto;
import com.iybook.sales.dto.request.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.request.OrderRequestFilterDto;
import com.iybook.sales.dto.response.OrderResponseDto;

import java.util.List;
import java.util.Map;

public interface SalesMapper {

    int selectOrderListCountByFilter(OrderRequestFilterDto filter);

    List<OrderDto> selectOrderListByFilterWithPaging(SingleOrderPagingRequestDto pagingInfo);

    List<OrderDto> selectOrderListByIdForChangeStatus(List<String> orderIdList);

    OrderResponseDto selectOrderDetailByOrderId(int orderId);


    int updateOrderStatusByOrderId(OrderStatusUpdateDto orderStatusChange);

    int updateOrderCancelAuto(Map<String, String> info);

}
