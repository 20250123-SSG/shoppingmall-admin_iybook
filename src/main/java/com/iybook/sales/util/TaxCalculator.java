package com.iybook.sales.util;

import com.iybook.sales.constants.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaxCalculator {

    public double calculate(int amount, String paymentType) {
        int taxPercent = Payment.getTaxPercent(paymentType);
        if (taxPercent == 0) {
            return amount;
        }
        double taxAmount = amount * taxPercent / 100.0;

        return amount - taxAmount;
    }

}
