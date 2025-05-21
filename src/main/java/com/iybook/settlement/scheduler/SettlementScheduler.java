package com.iybook.settlement.scheduler;

import com.iybook.settlement.dto.SettlementDto;
import com.iybook.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class SettlementScheduler {

    private final SettlementService settlementService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void changeSettlementStatus() {
        settlementService.updateSettlementStatus(SettlementDto.builder().exDate(LocalDate.now()).build());
    }
}
