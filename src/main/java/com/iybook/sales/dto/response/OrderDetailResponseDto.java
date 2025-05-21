package com.iybook.sales.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailResponseDto {

    private int orderDetailId;
    private String bookId;
    private String bookName;
    private String authorName;
    private int orderDetailTotalCount;
    private int orderDetailTotalPrice;

}
