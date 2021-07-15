package com.microservices.product.controller;

import com.microservices.product.model.dto.ProductDto;
import com.microservices.product.model.dto.StatusMessageDto;
import com.microservices.product.model.entity.ProductEntity;
import com.microservices.product.repository.ProductRepository;
import com.microservices.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<ProductEntity> productEntities = productRepository.findAll();
        return ResponseEntity.ok(productEntities);
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> publishProduct(@RequestBody ProductDto productDto){
        StatusMessageDto result = new StatusMessageDto<>();
        try{
            return productService.addProduct(productDto);
        }
        catch (Exception e){
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("/edit-product/{id}")
    public ResponseEntity<?> editProduct(@RequestBody ProductDto productDto, @PathVariable Integer id){
        StatusMessageDto result = new StatusMessageDto<>();
        try{
            return productService.editProduct(productDto, id);
        }
        catch(Exception e){
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        StatusMessageDto result = new StatusMessageDto<>();

        try{
            return productService.deleteProduct(id);
        }
        catch (Exception e){
            result.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        ProductEntity productEntity = productRepository.findByProductId(id);
        return ResponseEntity.ok(productEntity);
    }

}
