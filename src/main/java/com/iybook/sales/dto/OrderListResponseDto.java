package com.iybook.sales.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderListResponseDto {

    private int page;
    private int totalPage;
    private int beginPage;
    private int endPage;
    private int totalOrderCount;
    private List<OrderDto> orderList;

    public static OrderListResponseDto empty(){
        return new OrderListResponseDto(0, 0, 0, 0,0, new ArrayList<>());
    }

}
