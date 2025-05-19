package com.iybook.sales.util;

import com.iybook.sales.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class OldestOrderFirstSorter implements Comparator<OrderDto> {

    @Override
    public int compare(OrderDto o1, OrderDto o2) {
        return o1.getOrderDate().compareTo(o2.getOrderDate());
    }

}
