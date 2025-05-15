package com.iybook.notice.controller;

import com.iybook.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/notice")
@Controller
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/home.page")
    public void home(){}

}
