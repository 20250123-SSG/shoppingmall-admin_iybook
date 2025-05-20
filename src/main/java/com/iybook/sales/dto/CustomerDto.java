package com.iybook.sales.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CustomerDto {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerGender;
    private String customerAge;

}
