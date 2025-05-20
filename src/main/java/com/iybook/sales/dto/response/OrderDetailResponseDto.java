package com.iybook.sales.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
public class OrderDetailResponseDto {

    private int orderDetailId;
    private String bookId;
    private String bookName;
    private String orderDetailTotalCount;
    private String orderDetailTotalPrice;

}
