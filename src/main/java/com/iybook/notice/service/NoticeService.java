package com.iybook.notice.service;

import com.iybook.notice.dto.NoticeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface NoticeService {

    Map<String, Object> getNoticesAndPaging(int page);

    int registerNotice(NoticeDto board);

    NoticeDto getNoticeDetail(int no);
}
