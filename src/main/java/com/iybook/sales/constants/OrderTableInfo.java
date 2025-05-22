package com.iybook.sales.constants;

import lombok.Getter;

@Getter
public enum OrderTableInfo {

    DISPLAY(25),
    PAGE_PER_BLOCK(5);

    private final int value;

    OrderTableInfo(int value) {
        this.value = value;
    }

}
