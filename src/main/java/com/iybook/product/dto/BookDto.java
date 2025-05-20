package com.iybook.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookDto {

    private int bookId;
    private String bookName;
    private String authorName;
    private String publisher;
    private int bookPrice;
    private String bookImage;
    private String bookDescription;
    private int categoryId;
    private String publishedAt;
    private String createdAt;
    private String updatedAt;
    private String publishStatus;
    private int stock;

}
