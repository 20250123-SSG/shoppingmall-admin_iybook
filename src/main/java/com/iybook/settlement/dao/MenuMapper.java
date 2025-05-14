package com.iybook.settlement.dao;

import com.iybook.settlement.dto.MenuDto;

import java.util.List;

public interface MenuMapper {
    //반환타입 실행할sql의id(sql전달할데이터);

    List<MenuDto> selectMenuList();
    MenuDto selectMenuByCode(int code);
    int insertMenu(MenuDto menu);
    int updateMenu(MenuDto menu);
    int deleteMenu(String[] codes);

}
