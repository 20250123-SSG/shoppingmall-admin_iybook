package com.iybook.sales.util;

import com.iybook.sales.constants.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TaxCalculator {

    public Map<String, Integer> calculate(int amount, String paymentType) {
        int taxPercent = Payment.getTaxPercent(paymentType);

        double taxAmount;
        double settlementResult;

        if (taxPercent == 0) {
            taxAmount = 0;
            settlementResult = amount;
        } else {
            taxAmount = amount * taxPercent / 100.0;
            settlementResult = amount - taxAmount;
        }

        return Map.of(
                "tax", (int) taxAmount,
                "settlement", (int)settlementResult
        );
    }

}
