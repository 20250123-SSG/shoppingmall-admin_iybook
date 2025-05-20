package com.iybook.notice.service;

import com.iybook.notice.dao.NoticeMapper;
import com.iybook.notice.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import com.iybook.common.util.PageUtil;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final SqlSessionTemplate sqlSession;
    private final PageUtil pageUtil;

    @Override
    public Map<String, Object> getNoticesAndPaging(int page) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        int totalCount = noticeMapper.selectNoticeListCount();
        Map<String, Object> map = pageUtil.getPageInfo(totalCount, page, 5, 5);
        List<NoticeDto> list = noticeMapper.selectNoticeList(map);
        map.put("list", list);
        return map;
    }

    @Override
    public void toggleNoticeHiddenStatus(int noticeId) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        NoticeDto notice = noticeMapper.selectNoticeById(noticeId);
        String publishStatus = "숨김".equals(notice.getPublishStatus()) ? "게시" : "숨김";

        noticeMapper.updateNoticeHidden(noticeId, publishStatus);
    }

    @Override
    public int registerNotice(NoticeDto notice) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        int result = noticeMapper.insertNotice(notice);

        return result;
    }

    @Override
    public NoticeDto getNoticeDetail(int no) {

        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        NoticeDto notice = noticeMapper.selectNoticeById(no);

        return notice;
    }

    @Override
    public void deleteNoticesByIds(List<Integer> noticeIds) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        noticeMapper.deleteNoticesByIds(noticeIds);
    }
}
