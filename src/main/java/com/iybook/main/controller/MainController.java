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


    @GetMapping("/main") //   /user/signup.page  => /WEB-INF/views/user/signup.jsp
    public String loginPage(){
        return "main";
    }

}
