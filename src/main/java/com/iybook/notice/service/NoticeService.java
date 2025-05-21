package com.iybook.notice.service;

import com.iybook.notice.dto.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    Map<String, Object> getNoticesAndPaging(int page);

    int toggleNoticeHiddenStatus(int noticeId);

    void deleteNoticesByIds(List<Integer> noticeIds);
    void deleteNoticeById(int noticeId);


    int registerNotice(NoticeDto notice, int userId);

    NoticeDto getNoticeDetail(int no);
    List<NoticeDto> getNotices();
}
