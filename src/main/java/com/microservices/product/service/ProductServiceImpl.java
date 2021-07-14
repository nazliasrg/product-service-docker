package com.microservices.product.service;

import com.microservices.product.model.dto.ProductDto;
import com.microservices.product.model.dto.StatusMessageDto;
import com.microservices.product.model.entity.ProductEntity;
import com.microservices.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    KafkaTemplate<String, ProductEntity> kafkaTemplate;

    private static final String TOPIC = "ProductTopic";

    @Override
    public ResponseEntity<?> addProduct(ProductDto dto) {
        StatusMessageDto<ProductEntity> result = new StatusMessageDto<>();

        if(dto.getProductName() != null){
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductName(dto.getProductName());
            productEntity.setDescription(dto.getDescription());
            productEntity.setPrice(dto.getPrice());
            productEntity.setStock(dto.getStock());
            productEntity.setIsAvailable(1);
            productEntity.setImgSrc(dto.getImgSrc());
            productRepository.save(productEntity);
            productEntity.setProductCode("P" + productEntity.getProductId());
            productRepository.save(productEntity);

            kafkaTemplate.send(TOPIC, productEntity);

            result.setStatus(HttpStatus.OK.value());
            result.setMessage(productEntity.getProductName() + " has been added successfully!");
            result.setData(productEntity);
            return ResponseEntity.ok(result);
        }
        else{
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setMessage("Product Name is empty!");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Override
    public ResponseEntity<?> editProduct(ProductDto dto, Integer id) {
        StatusMessageDto<ProductEntity> result = new StatusMessageDto<>();

        ProductEntity productEntity = productRepository.findByProductId(id);

        if(dto.getProductCode() != null){
            productEntity.setProductCode(dto.getProductCode());
        }

        if(dto.getProductName() != null){
            productEntity.setProductName(dto.getProductName());
        }

        if(dto.getDescription() != null){
            productEntity.setDescription(dto.getDescription());
        }

        if(dto.getPrice() != null){
            productEntity.setPrice(dto.getPrice());
        }

        if(dto.getStock() != null){
            productEntity.setStock(dto.getStock());
        }

        if(dto.getIsAvailable() != null){
            productEntity.setIsAvailable(dto.getIsAvailable());
        }

        if(dto.getImgSrc() != null){
            productEntity.setImgSrc(dto.getImgSrc());
        }

        productRepository.save(productEntity);

        kafkaTemplate.send(TOPIC, productEntity);

        result.setStatus(HttpStatus.OK.value());
        result.setMessage("Product Code " + productEntity.getProductCode() + " has been changed successfully!");
        result.setData(productEntity);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Integer id) {
        StatusMessageDto result = new StatusMessageDto<>();

        ProductEntity productEntity = productRepository.findByProductId(id);
        productEntity.setStock(0);
        productEntity.setIsAvailable(0);
        productRepository.save(productEntity);

        kafkaTemplate.send(TOPIC, productEntity);

        result.setStatus(HttpStatus.OK.value());
        result.setMessage("Product " + productEntity.getProductName() + " data has been deleted!");
        result.setData(productEntity);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public String editProductFromTopic(ProductDto dto) {
        StatusMessageDto<ProductEntity> result = new StatusMessageDto<>();

        ProductEntity productEntity = productRepository.findByProductCode(dto.getProductCode());
        productEntity.setProductName(dto.getProductName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setPrice(dto.getPrice());
        productEntity.setStock(dto.getStock());
        productEntity.setIsAvailable(dto.getIsAvailable());
        productRepository.save(productEntity);

        result.setMessage("Product " + productEntity.getProductName() + " has been updated successfully!");
        return result.getMessage();
    }

    @Override
    public String addProductFromTopic(ProductDto dto) {
        StatusMessageDto<ProductEntity> result = new StatusMessageDto<>();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(dto.getProductName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setPrice(dto.getPrice());
        productEntity.setStock(dto.getStock());
        productEntity.setIsAvailable(dto.getIsAvailable());
        productRepository.save(productEntity);

        productEntity.setProductCode("P" + productEntity.getProductId());
        productRepository.save(productEntity);

        result.setMessage("Product " + productEntity.getProductName() + " has been added successfully!");
        return result.getMessage();
    }
}
