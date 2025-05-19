package com.iybook.sales.dto;

import java.util.List;

public record OrderStatusChangeDto(String orderStatus,
                                   List<String> orderIdList) {
}
