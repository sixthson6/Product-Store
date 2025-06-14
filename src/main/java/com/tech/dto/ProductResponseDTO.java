package com.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String categoryName;

    public ProductResponseDTO(Long id, String name, String shortDescription, BigDecimal price, String imageUrl, String categoryName) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }
}
