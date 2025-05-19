package com.iybook.notice.controller;

import com.iybook.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice")
@Controller
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/noticeList.page")
    public String boardListPage(@RequestParam(value="page", defaultValue="1") int page, Model model){
        log.debug("사용자가 요청한 페이지: {}", page);

        Map<String, Object> map = noticeService.getNoticesAndPaging(page);
        model.addAttribute("list", map.get("list"));   // 공지사항 리스트
        model.addAttribute("page", map.get("page"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));

        return "notice/noticeList";
    }

    @PostMapping("/toggleStatus.do")
    public String toggleStatus(@RequestParam Long noticeId, @RequestParam boolean currentHidden) {
        log.debug("토글 요청 noticeId={}, currentHidden={}", noticeId, currentHidden);
        noticeService.toggleNoticeHiddenStatus(noticeId, currentHidden);
        return "redirect:/notice/noticeList.page";
    }
}
