package com.iybook.product.service;

import com.iybook.product.dto.BookDto;
import com.iybook.product.dto.BookFilterDto;
import com.iybook.product.dto.BookStatsDto;
import com.iybook.product.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface ProductService {
    // 상품 통계 조회
    BookStatsDto getBookStats();

    // 상품전체조회
    List<BookDto> getBookList();
    // 상품 필터 조회
    List<BookDto> getBookListByFilter(BookFilterDto bookFilterDto);
    // 상품등록
    int registBook(BookDto book);
    // 상품수정
    int updateBookById(BookDto book);
    // 상품일괄 판매변경
    int updatePublishStatus(Map<String, Object> map);
    // 상품삭제
    int deleteBooks(List<String> bookIds);
    // id로 상품조회
    BookDto getBookById(String id);

    // 카테고리 조회
    List<CategoryDto> getCategoryList();

    // 카테고리 등록
    // 카테고리 수정
    // 카테고리 삭제
}
