package com.iybook.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDto {

    private int categoryId;
    private String categoryName;
    private String createdAt;
    private String updatedAt;

}
