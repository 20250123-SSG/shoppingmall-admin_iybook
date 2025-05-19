package com.iybook.sales.dto;

import java.util.List;

public record OrderStatusChangeResult(List<String> successIds,
                                      List<String> failedIds)
{
}
