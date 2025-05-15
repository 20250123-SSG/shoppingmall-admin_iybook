package com.iybook.sales.service;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {

    private final SqlSessionTemplate sqlSession;

}
