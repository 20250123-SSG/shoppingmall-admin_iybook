package com.iybook.sales.dto.request;

public record SingleOrderPagingRequestDto(int offset,
                                          int display,
                                          OrderRequestFilterDto filter)
{
}
