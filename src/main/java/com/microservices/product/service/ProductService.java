package com.microservices.product.service;

import com.microservices.product.model.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> addProduct(ProductDto productDto);
    ResponseEntity<?> editProduct(ProductDto productDto, Integer productId);
    ResponseEntity<?> deleteProduct(Integer productId);
    String editProductFromTopic(ProductDto productDto);
    String addProductFromTopic(ProductDto productDto);
}
