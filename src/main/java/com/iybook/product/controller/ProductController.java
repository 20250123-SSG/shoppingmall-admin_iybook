package com.iybook.product.controller;

import com.iybook.common.util.FileUtil;
import com.iybook.common.util.PageUtil;
import com.iybook.product.dto.BookDto;
import com.iybook.product.dto.BookFilterDto;
import com.iybook.product.dto.BookStatsDto;
import com.iybook.product.dto.CategoryDto;
import com.iybook.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final FileUtil fileUtil;
    private final PageUtil pageUtil;

    @GetMapping("/list.page")
    public String bookList(BookFilterDto bookFilter,
                           @RequestParam(defaultValue = "1") int page,
                           Model model,
                           HttpServletRequest request){
        log.debug("list 호출");

        // 검색필터
        model.addAttribute("filter", bookFilter);
        // status 개수 조회
        BookStatsDto bookStats = productService.getBookStats();
        model.addAttribute("stats", bookStats);
        // 카테고리 조회
        List<CategoryDto> categoryList = productService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        // pagination
        // 전체 도서 수 조회
        int totalCount = productService.getBookCountByFilter(bookFilter);

        // pagination 정보 계산
        int display = 10;
        int pagePerBlock = 5;
        Map<String, Object> pageInfo = pageUtil.getPageInfo(totalCount, page, display, pagePerBlock);
        model.addAttribute("pageInfo", pageInfo);

        // productList 호출 (페이지 적용)
        bookFilter.setOffset((int) pageInfo.get("offset"));
        bookFilter.setLimit(display);
        List<BookDto> bookList = productService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", bookList);

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "product/bookTable";
        }

        return "product/list"; // 전체페이지 호출
    }

    @PostMapping("/update.do")
    @ResponseBody
    public Map<String, Object> updateBooks(@RequestBody Map<String, Object> payload){
        List<String> bookIds = (List<String>)payload.get("bookIds");
        int result = productService.updatePublishStatus(payload);
        if (bookIds.size() == result) {
            return Map.of("success", true, "resultCount", result);
        }
        return Map.of("success", false);
    }

    @GetMapping("/regist.page")
    public void registPage(Model model){
        List<CategoryDto> categoryList = productService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
    }

    @PostMapping("/regist.do")
    public String registDo(BookDto book,
                           @RequestParam("image") MultipartFile bookImage,
                           RedirectAttributes redirectAttributes){
        if (!bookImage.isEmpty()) {
            Map<String, String> fileInfo = fileUtil.fileupload("product", bookImage);
            book.setBookImage(fileInfo.get("filePath") + "/" + fileInfo.get("filesystemName"));
        }
        int result = productService.registBook(book);
        redirectAttributes.addFlashAttribute("message", result > 0 ? "상품등록에 성공하였습니다." : "상품등록 실패");
        return "redirect:/product/list.page";
    }

    @GetMapping("/update.page")
    public void updatePage(String bookId, Model model){
        // 카테고리 조회
        List<CategoryDto> categoryList = productService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        // id로 상품조회
        BookDto resultBook = productService.getBookById(bookId);
        model.addAttribute("book", resultBook);
    }

    @PostMapping("/updatebook.do")
    public String updateDo(BookDto book,
                       @RequestParam("image") MultipartFile bookImage,
                       RedirectAttributes redirectAttributes){
        log.debug("bookDTO: " + book);
        if (!bookImage.isEmpty()) {
            Map<String, String> fileInfo = fileUtil.fileupload("product", bookImage);
            book.setBookImage(fileInfo.get("filePath") + "/" + fileInfo.get("filesystemName"));
        }
        log.debug("file 추가: " + book);
        int result = productService.updateBookById(book);
        log.debug("완료 "+ result);
        redirectAttributes.addFlashAttribute("message", result > 0 ? "상품수정에 성공하였습니다." : "상품수정 실패");
        return "redirect:/product/list.page";
    }

}
