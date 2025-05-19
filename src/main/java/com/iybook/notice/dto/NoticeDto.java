package com.iybook.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NoticeDto {
    private int noticeId;
    private String userId;
    private String title;
    private String description;
    private int readCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String publish_status;
}
