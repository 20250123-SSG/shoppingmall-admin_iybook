package com.iybook.main.controller;

import com.iybook.main.dto.UserDto;
import com.iybook.main.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MainController {


    @GetMapping("/main")
    public String mainPage(HttpSession session){

        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login.page"; // 로그인 안 되어 있으면 로그인 페이지로
        }

        return "main";
    }

}
