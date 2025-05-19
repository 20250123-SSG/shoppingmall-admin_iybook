package com.iybook.notice.dao;

import com.iybook.notice.dto.NoticeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    int selectNoticeListCount();
    List<NoticeDto> selectNoticeList(Map<String, Object> map);

    void updateNoticeHidden(@Param("noticeId") Long noticeId, @Param("hidden") boolean publishStatus);

    int insertNotice(NoticeDto board);

    NoticeDto selectNoticeByNo(int no);

    void deleteNoticesByIds(@Param("noticeIds") List<Long> noticeIds);

}
