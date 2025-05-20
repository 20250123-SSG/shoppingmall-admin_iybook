package com.iybook.statistics.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StatisticsCategoryDto {
    private String categoryName;
    private int orderCount;
    private String gender;      // selectCategoryStatsByGender 용
    private String ageGroup;    // selectCategoryStatsByAgeGroup 용
}
