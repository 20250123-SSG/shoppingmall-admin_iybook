package com.iybook.sales.dto;

public record SingleOrderPagingRequestDto(int offset,
                                          int display,
                                          OrderRequestFilterDto filter)
{
}
