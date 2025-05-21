package com.iybook.sales.dto.request;


import com.iybook.sales.dto.OrderDto;

public record OrderStatusUpdateDto(OrderDto order,
                                   String currentStatus,
                                   String newStatus)
{
}