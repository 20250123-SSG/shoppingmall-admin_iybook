package com.iybook.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value={"/", "/main.page"})
    public String mainPage(){
        return "main";
    }

}
