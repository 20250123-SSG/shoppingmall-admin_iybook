package com.iybook.sales.constants;

import lombok.Getter;

@Getter
public enum SettlementStatus {

    PENDING("정산예정"),
    COMPLETED("정산완료")
    ;

    private final String value;

    SettlementStatus(String description) {
        this.value = description;
    }


}
