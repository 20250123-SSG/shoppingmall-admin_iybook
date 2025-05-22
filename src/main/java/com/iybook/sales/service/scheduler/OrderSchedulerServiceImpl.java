package com.iybook.sales.service.scheduler;

import com.iybook.sales.dao.SalesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Service
public class OrderSchedulerServiceImpl implements OrderSchedulerService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public void updateOrderCancelAuto(Map<String, String> info) {
        SalesMapper salesMapper = sqlSession.getMapper(SalesMapper.class);

        int result = salesMapper.updateOrderCancelAuto(info);
        if (result > 0) {
            log.info("[자동취소] 주문 상태 '{}' → '{}' 변경 완료: {}건 (기준: {}일 초과)",
                    info.get("currentStatus"),
                    info.get("newStatus"),
                    result,
                    info.get("expirationDays")
            );
        } else {
            log.warn("[자동취소] 변경 사항 없음");
        }
    }

}
