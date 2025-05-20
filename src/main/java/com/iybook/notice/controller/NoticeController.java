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

    @GetMapping("/noticeList.page")
    public String boardListPage(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        log.debug("사용자가 요청한 페이지: {}", page);

        Map<String, Object> map = noticeService.getNoticesAndPaging(page);
        model.addAttribute("list", map.get("list"));   // 공지사항 리스트
        model.addAttribute("page", map.get("page"));
        model.addAttribute("totalPage", map.get("totalPage"));
        model.addAttribute("beginPage", map.get("beginPage"));
        model.addAttribute("endPage", map.get("endPage"));

        return "notice/noticeList";
    }

    @GetMapping("/registNotice.page")
    public String showRegistNotice() {
        return "notice/registNotice";
    }

    @PostMapping("/toggleStatus.do")
    @ResponseBody
    public Map<String, Object> toggleStatus(@RequestParam int noticeId) {
        int result = noticeService.toggleNoticeHiddenStatus(noticeId);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            NoticeDto updated = noticeService.getNoticeDetail(noticeId); // 변경된 값 조회
            response.put("success", true);
            response.put("publishStatus", updated.getPublishStatus());
            response.put("updatedAt", updated.getUpdatedAt());
        } else {
            response.put("success", false);
            response.put("message", "상태 변경 실패");
        }
        return response;
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
