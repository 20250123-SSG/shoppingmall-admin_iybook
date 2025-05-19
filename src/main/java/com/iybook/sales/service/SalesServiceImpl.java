package com.iybook.sales.service;

import com.iybook.sales.constants.OrderTableInfo;
import com.iybook.sales.dao.SalesMapper;
import com.iybook.sales.dto.OrderDto;
import com.iybook.sales.dto.SingleOrderPagingRequestDto;
import com.iybook.sales.dto.OrderListResponseDto;
import com.iybook.sales.dto.OrderRequestFilterDto;
import com.iybook.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SqlSessionTemplate sqlSession;
    private final PageUtil pageUtil;

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

}
