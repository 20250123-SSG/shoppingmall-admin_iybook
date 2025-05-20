package com.iybook.sales.dto;

import java.util.List;

public record OrdersStatusUpdateDto(String orderStatus,
                                    List<String> orderIdList)
{
}
