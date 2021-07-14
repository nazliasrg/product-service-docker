package com.microservices.product.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(unique = true)
    private String productCode;

    @Column
    private String productName;

    @Column
    private String description;

    @Column
    private Double price;

    @Column(length = 3)
    private Integer stock;

    @Column(length = 1)
    private Integer isAvailable;

    @Column
    private String imgSrc;

    public ProductEntity(String productCode, String productName, String description, Double price, Integer stock, Integer isAvailable, String imgSrc) {
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isAvailable = isAvailable;
        this.imgSrc = imgSrc;
    }
}
