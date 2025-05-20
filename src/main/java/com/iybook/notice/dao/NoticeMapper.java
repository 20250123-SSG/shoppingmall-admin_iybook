package com.iybook.notice.dao;

import com.iybook.notice.dto.NoticeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {
    int selectNoticeListCount();
    List<NoticeDto> selectNoticeList(Map<String, Object> map);

    int updateNoticeHidden(@Param("noticeId") int noticeId, @Param("publishStatus") String publishStatus);

    int insertNotice(NoticeDto noticeDto);

    NoticeDto selectNoticeById(int id);

    void deleteNoticesByIds(@Param("noticeIds") List<Integer> noticeIds);

}
