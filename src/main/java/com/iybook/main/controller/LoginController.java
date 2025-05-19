package com.iybook.main.controller;

import com.iybook.main.dto.UserDto;
import com.iybook.main.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/login.page") //   /user/signup.page  => /WEB-INF/views/user/signup.jsp
    public void signupPage(){}


    @PostMapping("/login.do")
    public String signin(UserDto user, HttpSession session
            , RedirectAttributes redirectAttributes){

        UserDto selectedUser = userService.getUser(user);
        if(selectedUser != null){ // 로그인 성공
            session.setAttribute("loginUser", selectedUser);
            redirectAttributes.addFlashAttribute("message", selectedUser.getUserName() + "님 로그인 되었습니다.");
        }else { // 로그인 실패
            redirectAttributes.addFlashAttribute("message", "로그인 실패!");
        }

        return "redirect:/";
    }
}
