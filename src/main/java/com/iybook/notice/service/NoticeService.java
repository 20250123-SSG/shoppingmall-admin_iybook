package com.iybook.notice.service;

import com.iybook.notice.dto.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    Map<String, Object> getNoticesAndPaging(int page);

    void toggleNoticeHiddenStatus(Long noticeId, boolean currentHidden);

    void deleteNoticesByIds(List<Long> noticeIds);

    int registerNotice(NoticeDto board);

    NoticeDto getNoticeDetail(int no);
}
