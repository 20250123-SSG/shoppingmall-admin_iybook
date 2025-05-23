package com.iybook.product.dao;

import com.iybook.product.dto.BookDto;
import com.iybook.product.dto.BookFilterDto;
import com.iybook.product.dto.BookStatsDto;
import com.iybook.product.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    List<BookDto> selectBookListByFilter(BookFilterDto bookFilter);
    int insertBook(BookDto book);
    int updateBookById(BookDto book);
    int updatePublishStatus(Map<String, Object> map);
    BookStatsDto selectBookStats();
    List<CategoryDto> selectCategoryList();
    BookDto selectBookById(String id);
    int selectBookCountByFilter(BookFilterDto bookFilter);
}
