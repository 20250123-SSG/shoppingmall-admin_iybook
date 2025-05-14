package com.iybook.login.service;

import com.iybook.login.dao.MenuMapper;
import com.iybook.login.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public List<MenuDto> getMenuAll() {
        return sqlSession.getMapper(MenuMapper.class).selectMenuList();
    }

    @Override
    public MenuDto getMenuByCode(int code) {
        return sqlSession.getMapper(MenuMapper.class).selectMenuByCode(code);
    }

    @Override
    public int registMenu(MenuDto menu) {
        return sqlSession.getMapper(MenuMapper.class).insertMenu(menu);
    }

    @Override
    public int modifyMenu(MenuDto menu) {
        return sqlSession.getMapper(MenuMapper.class).updateMenu(menu);
    }

    @Override
    public int removeMenu(String[] codes) {
        return sqlSession.getMapper(MenuMapper.class).deleteMenu(codes);
    }

    // 트랜잭션 테스트 (두개의 메뉴 일괄등록 - 둘 중에 하나의 메뉴 등록시 실패되면 rollback)
    /*
    @Override
    public int registTwoMenu() {

        // 테스트용 메뉴 2개 세팅
        MenuDto menu1 = MenuDto.builder()
                .menuName("트랜잭션테스트용")
                .menuPrice(1000)
                .categoryCode(4)
                .orderableStatus("Y")
                .build(); // insert성공될만한 메뉴
        MenuDto menu2 = new MenuDto(); // insert실패될만한 메뉴

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        int result1 = menuMapper.insertMenu(menu1); // 새로운 트랜잭션이 만들어짐
        int result2 = menuMapper.insertMenu(menu2); // 새로운 트랜잭션이 만들어짐

        return result1 * result2;
        // mybatis-spring에서는 기본적으로 각 쿼리실행때마다 새로운 트랜잭션을 만들어서
        // 각 트랜잭션으로 관리함. 별도의 예외가 발생되지 않으면 commit을 진행시킴
    }
     */

    // 트랜잭션 처리 방법1. 선언전 트랜잭션 @Transactional
    /*
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int registTwoMenu() {

        MenuDto menu1 = MenuDto.builder()
                .menuName("선언적트랜잭션테스트용")
                .menuPrice(2000)
                .categoryCode(5)
                .orderableStatus("N")
                .build();
        MenuDto menu2 = new MenuDto();

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        int result1 = menuMapper.insertMenu(menu1); // 최초로 트랜잭션이 만들어지고
        int result2 = menuMapper.insertMenu(menu2); // 기존에 존재하는 트랜잭션에 같이 묶임

        // 기본적으로 RuntimeException관련 예외가 발생되면 트랜잭션 rollback, 예외가 발생되지 않으면 commit

        return result1 * result2;
    }
     */

    // 트랜잭션 처리방법2. AOP Advice 등록
    @Override
    public int registTwoMenu() {

        MenuDto menu1 = MenuDto.builder()
                .menuName("AOP_Advice_트랜잭션테스트용")
                .menuPrice(3000)
                .categoryCode(6)
                .orderableStatus("Y")
                .build();
        MenuDto menu2 = new MenuDto();

        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        int result1 = menuMapper.insertMenu(menu1);
        int result2 = menuMapper.insertMenu(menu2);

        return result1 * result2;
    }
}
