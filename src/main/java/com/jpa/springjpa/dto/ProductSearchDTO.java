package com.jpa.springjpa.dto;

import java.util.List;

public record ProductSearchDTO(
        String searchValue,
        String category,
        double min_price,
        double max_price,
        List<String> brand
) {
}
