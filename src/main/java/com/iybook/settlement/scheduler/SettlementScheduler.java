package com.iybook.settlement.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SettlementScheduler {

    @Scheduled(cron = "0/10 * * * * *")
    public void changeSettlementStatus() {
        log.debug("change settlement status");
        System.out.println("change settlement status");
    }
}
