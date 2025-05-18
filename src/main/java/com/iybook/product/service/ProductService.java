package com.iybook.product.service;

import com.iybook.product.dto.ProductDto;

import java.util.List;

public interface ProductService {
    // 상품조회
    List<ProductDto> getProductList();
    // 상품등록
    int registProduct(ProductDto product);
    // 상품수정
    // 상품삭제
    // 카테고리 조회
    // 카테고리 등록
    // 카테고리 수정
    // 카테고리 삭제
}
