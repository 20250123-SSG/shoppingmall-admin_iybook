package com.iybook.main.controller;

import com.iybook.main.dto.UserDto;
import com.iybook.main.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    // 기본 경로 → 로그인으로 리다이렉트
//    @GetMapping("/")
//    public String index() {
//        return "redirect:/login.page";
//    }

    @GetMapping("/login.page") //   /user/signup.page  => /WEB-INF/views/user/signup.jsp
    public String loginPage(){
        return "login/login";
    }


    @PostMapping("/login.do")
    public String signin(UserDto user, HttpSession session, Model model) {
        UserDto selectedUser = userService.getUser(user);

        if (selectedUser != null) {
            // 로그인 성공: 사용자 정보를 세션에 저장
            session.setAttribute("loginUser", selectedUser);
            return "redirect:/main"; // 로그인 성공 후 메인 페이지로 리다이렉트
        } else {
            // 로그인 실패: 메시지 전달 후 로그인 페이지 다시 렌더링
            model.addAttribute("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
            model.addAttribute("userLoginId", user.getUserLoginId());
            return "login/login";
        }
    }

    @PostMapping("/logout.do")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.page"; // 로그인 페이지로 리다이렉트
    }
}
