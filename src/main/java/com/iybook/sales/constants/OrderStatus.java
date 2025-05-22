package com.iybook.sales.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {

    COMPLETED("주문완료"),
    CANCEL_REQUESTED("취소요청"),
    CANCELED("취소완료"),
    DELIVERED("배송완료");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

}
