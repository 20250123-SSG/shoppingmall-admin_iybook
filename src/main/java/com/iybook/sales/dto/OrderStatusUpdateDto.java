package com.iybook.sales.dto;


public record OrderStatusUpdateDto(OrderDto order,
                                   String currentStatus,
                                   String newStatus)
{
}