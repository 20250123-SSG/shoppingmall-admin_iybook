package com.iybook.notice.service;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final SqlSessionTemplate sqlSession;

}
