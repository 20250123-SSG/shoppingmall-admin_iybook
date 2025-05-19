package com.iybook.main.service;

import com.iybook.main.dao.UserMapper;
import com.iybook.main.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public int registerUser(UserDto user) {
        return sqlSession.getMapper(UserMapper.class).insertUser(user);
    }

    @Override
    public UserDto getUser(UserDto user) {

        UserDto selectedUser = sqlSession.getMapper(UserMapper.class).selectUserByLoginId(user.getUserLoginId());

        // 로그인실패
        if(selectedUser == null || !user.getUserPwd().equals(selectedUser.getUserPwd())){
            return null;
        }

        return selectedUser;
    }

}

