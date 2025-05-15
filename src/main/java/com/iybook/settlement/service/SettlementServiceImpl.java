package com.iybook.settlement.service;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {

    private final SqlSessionTemplate sqlSession;

}
