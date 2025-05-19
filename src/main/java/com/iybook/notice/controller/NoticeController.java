package com.iybook.notice.controller;

import com.iybook.notice.dto.NoticeDto;
import com.iybook.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
    public String toggleStatus(@RequestParam int noticeId) {
        noticeService.toggleNoticeHiddenStatus(noticeId);
        return "redirect:/notice/noticeList.page";
    }

    @PostMapping("/deleteSelected.do")
    public String deleteSelected(@RequestParam("noticeIds[]") List<Integer> noticeIds, RedirectAttributes redirectAttributes) {
        if (noticeIds == null || noticeIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "삭제할 공지사항을 선택해주세요.");
            return "redirect:/notice/noticeList.page";
        }

        noticeService.deleteNoticesByIds(noticeIds);
        redirectAttributes.addFlashAttribute("message", "선택한 공지사항이 삭제되었습니다.");
        return "redirect:/notice/noticeList.page";
    }
}
