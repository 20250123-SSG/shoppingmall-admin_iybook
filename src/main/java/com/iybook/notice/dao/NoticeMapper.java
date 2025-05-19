package com.iybook.notice.dao;

import com.iybook.notice.dto.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    int selectNoticeListCount();
    List<NoticeDto> selectNoticeList(Map<String, Object> map);

    int insertNotice(NoticeDto board);

    NoticeDto selectNoticeByNo(int no);

}
