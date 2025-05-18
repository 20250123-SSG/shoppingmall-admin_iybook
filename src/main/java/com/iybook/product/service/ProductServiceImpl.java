package com.iybook.product.service;

import com.iybook.product.dao.ProductMapper;
import com.iybook.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final SqlSessionTemplate sqlSession;

    @Override
    public List<ProductDto> getProductList() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.selectProductList();
    }

    @Override
    public int registProduct(ProductDto product) {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        return productMapper.insertProduct(product);
    }
}
