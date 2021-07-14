package com.microservices.product.repository;

import com.microservices.product.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
    ProductEntity findByProductCode(String productCode);
    ProductEntity findByProductId(Integer productId);
}
