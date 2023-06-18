package com.mmr.comsumer.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private int page = 0;
    private int size = 10;
    private String sort;
}
