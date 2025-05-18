package com.iybook.sales.dto;

public record SingleOrderPagingDto(int offset,
                                   int display,
                                   OrderRequestFilterDto filter)
{
}
