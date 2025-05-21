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
    public int toggleNoticeHiddenStatus(int noticeId) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        NoticeDto notice = noticeMapper.selectNoticeById(noticeId);
        String publishStatus = "숨김".equals(notice.getPublishStatus()) ? "게시" : "숨김";

        return noticeMapper.updateNoticeHidden(noticeId, publishStatus);
    }

    @Override
    public int registerNotice(NoticeDto noticeDto, int userId) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
        noticeDto.setUserId(userId);
        int result = noticeMapper.insertNotice(noticeDto);

        return result;
    }

    @Override
    public NoticeDto getNoticeDetail(int noticeId) {

        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        return noticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public void deleteNoticesByIds(List<Integer> noticeIds) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        noticeMapper.deleteNoticesByIds(noticeIds);
    }

    @Override
    public void deleteNoticeById(int noticeId) {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        noticeMapper.deleteById(noticeId);
    }

    public List<NoticeDto> getNotices() {
        NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);

        return noticeMapper.selectNotices();
    }
}
