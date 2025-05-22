package com.iybook.notice.controller;

import com.iybook.main.dto.UserDto;
import com.iybook.notice.dto.NoticeDto;
import com.iybook.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice")
@Controller
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/noticeDetail.do")
    public String getNoticeDetail(@RequestParam("noticeId") int noticeId, Model model, RedirectAttributes redirectAttributes) {
        NoticeDto notice = noticeService.getNoticeDetail(noticeId);
        if (notice == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 공지사항입니다.");
            return "redirect:/notice/noticeList.page";
        }
        model.addAttribute("notice", notice);
        return "notice/noticeDetail";
    }

    @GetMapping("/noticeList.page")
    public String boardListPage(@RequestParam(value = "page", defaultValue = "1") int page, HttpSession session, Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login.page"; // 로그인 안 되어 있으면 로그인 페이지로
        }

        log.debug("사용자가 요청한 페이지: {}", page);

        Map<String, Object> map = noticeService.getNoticesAndPaging(page);
        model.addAttribute("list", map.get("list"));   // 공지사항 리스트
        model.addAttribute("page", map.get("page"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));

        return "notice/noticeList";
    }

    @GetMapping("/home")
    @ResponseBody
    public List<NoticeDto> getNotices() {
        return noticeService.getNotices();
    }

    @GetMapping("/registNotice.page")
    public String showRegistNotice(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login.page"; // 로그인 안 되어 있으면 로그인 페이지로
        }

        return "notice/registNotice";
    }

    @PostMapping("/toggleStatus.do")
    public String toggleStatus(@RequestParam int noticeId, RedirectAttributes redirectAttributes) {
        int result = noticeService.toggleNoticeHiddenStatus(noticeId);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("message", "상태가 변경되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "상태 변경에 실패했습니다.");
        }
        return "redirect:/notice/noticeList.page";
    }

    @PostMapping("/detail/toggleStatus.do")
    public String detailToggleStatus(@RequestParam int noticeId, RedirectAttributes redirectAttributes) {
        int result = noticeService.toggleNoticeHiddenStatus(noticeId);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("message", "상태가 변경되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "상태 변경에 실패했습니다.");
        }
        return "redirect:/notice/noticeDetail.do?noticeId=" + noticeId;
    }

    @PostMapping("/regist.do")
    public String registNotice(@RequestParam String title,
                               @RequestParam String description, HttpSession session, RedirectAttributes redirectAttributes) {
        NoticeDto notice = new NoticeDto();
        notice.setTitle(title);
        notice.setDescription(description);
        notice.setPublishStatus("게시"); // 기본 게시 상태

        UserDto loginUser = (UserDto) session.getAttribute("loginUser");

        int result = noticeService.registerNotice(notice, loginUser.getUserId());

        redirectAttributes.addFlashAttribute("message", result > 0 ? "등록 성공" : "등록 실패");
        return "redirect:/notice/noticeList.page";
    }

    @PostMapping("/delete.do")
    public String deleteNotice(@RequestParam("noticeId") int noticeId, RedirectAttributes redirectAttributes) {
        try {
            noticeService.deleteNoticeById(noticeId);
            redirectAttributes.addFlashAttribute("message", "공지사항이 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "공지사항 삭제 중 오류가 발생했습니다.");
        }
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
