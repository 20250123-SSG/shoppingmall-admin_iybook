package com.iybook.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BookStatsDto {
    private int all;
    private int sell;
    private int sold;
    private int end;
}
