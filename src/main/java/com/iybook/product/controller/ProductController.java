package com.iybook.product.controller;

import com.iybook.product.dto.BookDto;
import com.iybook.product.dto.BookFilterDto;
import com.iybook.product.dto.BookStatsDto;
import com.iybook.product.dto.CategoryDto;
import com.iybook.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list.page")
    public void list(BookFilterDto bookFilter, Model model){
        // 검색필터
        model.addAttribute("filter", bookFilter);
        // status 개수 조회
        BookStatsDto bookStats = productService.getBookStats();
        model.addAttribute("stats", bookStats);
        // 카테고리 조회
        List<CategoryDto> categoryList = productService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        // productList 호출
        List<BookDto> bookList = productService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", bookList);
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

}
