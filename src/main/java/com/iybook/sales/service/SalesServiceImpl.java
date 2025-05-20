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



@Slf4j
@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SqlSessionTemplate sqlSession;
    private final OrderAcceptService orderAcceptService;
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

    @Override
    public Map<String, List<String>> acceptOrders(List<String> orderIdList) {
        SalesMapper salesMapper = sqlSession.getMapper(SalesMapper.class);

        List<OrderDto> orderList = getOrderListForChangeStatus(orderIdList, salesMapper);
        orderList.sort(sorter);

        List<String> success = new ArrayList<>();
        List<String> fail = new ArrayList<>();

        for (OrderDto order : orderList) {
            try {
                boolean result = orderAcceptService.acceptOrder(
                        new OrderStatusUpdateDto(
                                order,
                                OrderStatus.COMPLETED.getValue(),
                                OrderStatus.DELIVERED.getValue()
                        )
                );
                if (result) {
                    success.add(order.getOrderId());
                } else {
                    fail.add(order.getOrderId());
                }
            }catch (Exception e) {
                fail.add(order.getOrderId());
            }
        }

        return Map.of(
                "success", success,
                "fail", fail
        );
    }






    public void cancelOrder() {
    }

    public void approveCancelRequest() {
    }

    public void rejectCancelRequest() {
    }



//    @Transactional
//    public void 주문처리부모메서드(List<String> orderIdList) {
//        for(String orderId : orderIdList) {
//
//        }
//    }
//
//    @Transactional(propagation = REQUIRES_NEW)
//    public void 주문하나하나처리자식메서드(String orderId, String orderStatus) {
//
//    }















    private List<OrderDto> getOrderListForChangeStatus(List<String> orderIdList, SalesMapper salesMapper) {
        return salesMapper.selectOrderListByIdForChangeStatus(orderIdList);
    }


//    //이거를 꼭 validateOrderList로 가져올 이유가 없디ㅏ.
//    private List<OrderDto> getValidOrderList(List<OrderDto> orderList, String orderStatus) {
//        return orderList.stream()
//                .filter(order -> order.getOrderStatus().equals(orderStatus))
//                .toList();
//    }
//
//    private List<OrderDto> getInvalidOrders(List<OrderDto> orderList, String orderStatus) {
//        return orderList.stream()
//                .filter(order -> !order.getOrderStatus().equals(orderStatus))
//                .toList();
//    }

}
