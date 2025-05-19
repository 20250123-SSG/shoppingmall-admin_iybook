package com.iybook.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookFilterDto {
    private String bookId;
    private String bookName;
    private String publisher;
    private String status;
    private Integer categoryId;
    private String dateType;
    private String startDate;
    private String endDate;
    private int dsBtn;

}
