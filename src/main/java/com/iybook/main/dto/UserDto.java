package com.iybook.main.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private long userId;
    private String userLoginId;
    private String userPwd;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}