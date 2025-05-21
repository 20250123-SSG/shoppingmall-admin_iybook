package com.iybook.sales.scheduler;

import com.iybook.sales.constants.OrderStatus;
import com.iybook.sales.service.SalesService;
import com.iybook.sales.service.scheduler.OrderSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderAutoCancelScheduler {

    private final static String ORDER_EXPIRATION_DAYS = "7";

    private final OrderSchedulerService orderSchedulerService;

    @Scheduled(cron = "0 0 0 * * *")
    public void autoCancel() {
        Map<String, String> info = Map.of(
                "currentStatus", OrderStatus.COMPLETED.getValue(),
                "newStatus", OrderStatus.CANCELED.getValue(),
                "expirationDays",ORDER_EXPIRATION_DAYS
        );

        orderSchedulerService.updateOrderCancelAuto(info);
    }

}
