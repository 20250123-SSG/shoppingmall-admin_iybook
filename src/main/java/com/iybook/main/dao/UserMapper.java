package com.iybook.main.dao;

import com.iybook.main.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(UserDto userDto);
    UserDto selectUserByLoginId(String userLoginId);
    int selectUser(UserDto userDto);
}
