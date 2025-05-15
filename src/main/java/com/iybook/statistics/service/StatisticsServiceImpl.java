package com.iybook.statistics.service;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final SqlSessionTemplate sqlSession;

}
