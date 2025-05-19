package com.iybook.product.service;

import com.iybook.product.dao.ProductMapper;
import com.iybook.product.dto.BookDto;
import com.iybook.product.dto.BookFilterDto;
import com.iybook.product.dto.BookStatsDto;
import com.iybook.product.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public BookStatsDto getBookStats() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.selectBookStats();
    }

    @Override
    public List<BookDto> getBookList() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.selectBookList();
    }

    @Override
    public List<BookDto> getBookListByFilter(BookFilterDto bookFilter) {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.selectBookListByFilter(bookFilter);
    }

    @Override
    public int registBook(BookDto book) {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.insertBook(book);
    }

    @Override
    public int updateBookById(BookDto book) {
        return 0;
    }

    @Override
    public int updatePublishStatus(Map<String, Object> map) {
        return 0;
    }

    @Override
    public List<CategoryDto> getCategoryList() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.selectCategoryList();
    }
}
