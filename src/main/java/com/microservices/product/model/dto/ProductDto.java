package com.microservices.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productCode;
    private String productName;
    private String description;
    private Double price;
    private Integer stock;
    private Integer isAvailable;
    private String imgSrc;
}
