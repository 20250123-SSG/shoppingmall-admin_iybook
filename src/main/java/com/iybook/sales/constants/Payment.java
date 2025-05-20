package com.iybook.sales.constants;

import lombok.Getter;

@Getter
public enum Payment {

    CARD("카드"),
    CASH("현금")
    ;

    private final String value;

    Payment(String value) {
        this.value = value;
    }

}
