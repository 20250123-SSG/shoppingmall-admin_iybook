package com.iybook.main.dao;

import com.iybook.main.dto.UserDto;


public interface UserMapper {

    int insertUser(UserDto userDto);
    int selectUser(UserDto userDto);
    UserDto selectUserById(String userId);
}
