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
    private int userId;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String publishStatus;
}
