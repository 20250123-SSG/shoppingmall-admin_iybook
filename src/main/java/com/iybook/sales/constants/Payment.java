package com.iybook.sales.constants;

import lombok.Getter;

@Getter
public enum Payment {

    CARD("카드", 10),
    CASH("현금", 8);

    private final String value;
    private final int taxPercent;

    Payment(String value, int taxPercent) {
        this.value = value;
        this.taxPercent = taxPercent;
    }

    public static int getTaxPercent(String paymentType) {
        for (Payment payment : Payment.values()) {
            if (payment.getValue().equalsIgnoreCase(paymentType)) {
                return payment.getTaxPercent();
            }
        }
        return 0;
    }

}
