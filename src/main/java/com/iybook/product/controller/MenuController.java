package com.iybook.product.controller;

import com.iybook.product.dto.MenuDto;
import com.iybook.product.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RequestMapping("/menu")
@Controller
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/list.page")
    public void menuListPage(Model model){
        model.addAttribute( "list", menuService.getMenuAll() );
        //return "menu/list";
    }

    @GetMapping("/regist.page")
    public void menuRegistPage(){}

    @PostMapping("/regist.do")
    public String menuRegist(MenuDto menu, RedirectAttributes redirectAttributes){
        int result = menuService.registMenu(menu);

        String message = null;
        if(result > 0){ // 성공
            message = menu.getMenuCode() + "번 메뉴로 성공적으로 등록되었습니다.";
        }else{ // 실패
            message = "등록에 실패하였습니다.";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/menu/list.page";
    }

    @GetMapping("/detail.page") //    /menu/detail.page?code=xx
    public void menuDetailPage(int code, Model model){
        model.addAttribute("menu", menuService.getMenuByCode(code));
        //return "menu/detail";
    }

    @GetMapping("/modify.page") //   /menu/modify.page?code=xx
    public void menuModifyPage(int code, Model model){
        model.addAttribute("menu", menuService.getMenuByCode(code));
        //return "menu/modify";
    }
    @PostMapping("/modify.do")
    public String menuModify(MenuDto menu, RedirectAttributes redirectAttributes){
        int result = menuService.modifyMenu(menu);

        String message = null;
        if(result > 0){
            message = "성공적으로 메뉴가 수정되었습니다.";
        }else{
            message = "수정에 실패하였습니다.";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/menu/detail.page?code=" + menu.getMenuCode();

    }

    @PostMapping("/remove.do") //    /menu/remove.do?code=xx&code=xx&code=xx
    public String menuRemove(String[] code, RedirectAttributes redirectAttributes){ // code = ["xx", "xx", "xx"]
        int result = menuService.removeMenu(code);

        String message = null;
        if(result == code.length){
            message = result + "개의 메뉴가 성공적으로 삭제되었습니다.";
        }else{
            message = "메뉴 삭제에 실패하였습니다.";
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/menu/list.page";
    }

    @GetMapping("/transaction.do")
    public void transactionTest(){
        menuService.registTwoMenu();
    }

}
