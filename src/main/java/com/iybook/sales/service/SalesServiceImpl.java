package com.iybook.sales.service;

import com.iybook.sales.constants.OrderStatus;
import com.iybook.sales.constants.OrderTableInfo;
import com.iybook.sales.dao.SalesMapper;
import com.iybook.sales.dto.*;
import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.common.util.PageUtil;
import com.iybook.sales.util.OldestOrderFirstSorter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SqlSessionTemplate sqlSession;
    private final PageUtil pageUtil;
    private final OldestOrderFirstSorter sorter;

    @Override
    public OrderListResponseDto getOrderListAndPageInfoByFilter(int page, OrderRequestFilterDto filter) {
        SalesMapper salesMapper = sqlSession.getMapper(SalesMapper.class);

        int orderCountResult = salesMapper.selectOrderListCountByFilter(filter);
        Map<String, Object> pageInfo = pageUtil.getPageInfo(
                orderCountResult,
                page,
                OrderTableInfo.DISPLAY.getValue(),
                OrderTableInfo.PAGE_PER_BLOCK.getValue()
        );
        SingleOrderPagingRequestDto paging = new SingleOrderPagingRequestDto(
                (Integer) pageInfo.get("offset"),
                (Integer) pageInfo.get("display"),
                filter
        );
        List<OrderDto> orderListResult = salesMapper.selectOrderListByFilterWithPaging(paging);

        return new OrderListResponseDto(
                page,
                (Integer) pageInfo.get("totalPage"),
                (Integer) pageInfo.get("beginPage"),
                (Integer) pageInfo.get("endPage"),
                orderCountResult,
                orderListResult
        );
    }

    /**
     * 받아온 리스트에서 주문이 불가능한것과 가능한 상태의 데이터를 나누고 일단 주문이 가능한 상태의 것을 처리하고 불가능인것이 있으면 예외처리
     * @param orderIdList
     * @return
     */
    @Override
    public OrderStatusChangeResult acceptOrder(List<String> orderIdList) {
        SalesMapper salesMapper = sqlSession.getMapper(SalesMapper.class);

        List<OrderDto> orderIdListForValidate = getOrderListWithOrderDate(orderIdList, salesMapper);
        orderIdListForValidate.sort(sorter);

        List<OrderDto> validOrders = getValidOrderList(orderIdListForValidate, OrderStatus.COMPLETED.getValue());

        List<OrderDto> invalidOrders = null;
        if(orderIdListForValidate.size() != validOrders.size()) {
            invalidOrders = getInvalidOrders(orderIdListForValidate, OrderStatus.COMPLETED.getValue());
        }

        OrderStatusChangeResult result = new OrderStatusChangeResult(
                getOrderIdList(validOrders),
                getOrderIdList(invalidOrders)
        );

        log.debug("fagwrgsergsergsergsergsergerg{}",result.successIds());


        int updateResult = salesMapper.updateOrderStatusByOrderId(
                new OrdersStatusUpdateDto(
                        OrderStatus.DELIVERED.getValue(),
                        result.successIds()
                )
        );
        if(updateResult != validOrders.size()) {
            throw new IllegalArgumentException("주문완료 데이터 수락 실패");
        }

        return result;
    }

    public void cancelOrder() {
    }

    public void approveCancelRequest() {
    }

    public void rejectCancelRequest() {
    }


    private List<OrderDto> getOrderListWithOrderDate(List<String> orderIdList, SalesMapper salesMapper) {
        return salesMapper.selectOrderListByIdForChangeStatus(orderIdList);
    }

    private List<String> getOrderIdList(List<OrderDto> orderList) {
        if(orderList == null || orderList.isEmpty()) {
            return new ArrayList<>();
        }
        return orderList.stream().map(OrderDto::getOrderId).collect(Collectors.toList());
    }

    private List<OrderDto> getValidOrderList(List<OrderDto> orderList, String orderStatus) {
        return orderList.stream()
                .filter(order -> order.getOrderStatus().equals(orderStatus))
                .toList();
    }

    private List<OrderDto> getInvalidOrders(List<OrderDto> orderList, String orderStatus) {
        return orderList.stream()
                .filter(order -> !order.getOrderStatus().equals(orderStatus))
                .toList();
    }

}
