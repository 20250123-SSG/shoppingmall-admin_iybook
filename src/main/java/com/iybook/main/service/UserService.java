package com.iybook.main.service;

import com.iybook.main.dto.UserDto;

public interface UserService {
    // 단수 파일 글등록
    int registerUser(UserDto user);
    UserDto getUser(UserDto user);
}
