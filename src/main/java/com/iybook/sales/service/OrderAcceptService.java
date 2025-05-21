package com.iybook.sales.service;

import com.iybook.sales.dto.request.OrderStatusUpdateDto;

public interface OrderAcceptService {

    boolean acceptOrder(OrderStatusUpdateDto updateInto);

}
