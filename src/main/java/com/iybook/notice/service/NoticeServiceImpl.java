package com.iybook.notice.service;

import com.iybook.notice.dao.NoticeMapper;
import com.iybook.notice.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final SqlSessionTemplate sqlSession;
    private final PageUtil pageUtil;

    @Override
    public Map<String, Object> getNoticesAndPaging(int page) { // int page == 현재 요청한 페이지 번호

        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        int totalCount = noticeMapper.selectNoticeListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 5, 5);
        List<NoticeDto> list = noticeMapper.selectNoticeList(map);
        map.put("list", list);
        // map : {totalPage:xx, beginPage:xx, endPage:xx, .., list:List<BoardDto>}

        return map;
    }

    @Override
    public int registerNotice(NoticeDto notice) {
        NoticeMapper boardMapper = sqlSession.getMapper(NoticeMapper.class);

        int result = boardMapper.insertNotice(notice);

        return result;
    }

    @Override
    public NoticeDto getNoticeDetail(int no) {

        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        NoticeDto notice = noticeMapper.selectNoticeByNo(no);

        return notice;
    }
}
