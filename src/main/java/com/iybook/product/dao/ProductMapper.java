package com.iybook.product.dao;

import com.iybook.product.dto.ProductDto;

import java.util.List;

public interface ProductMapper {
    List<ProductDto> selectProductList();
    int insertProduct(ProductDto product);
}
