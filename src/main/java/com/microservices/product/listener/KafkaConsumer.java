package com.microservices.product.listener;

import com.microservices.product.model.dto.ProductDto;
import com.microservices.product.model.dto.StatusMessageDto;
import com.microservices.product.repository.ProductRepository;
import com.microservices.product.service.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "ProductTopic", groupId = "group_product2")
    public void consumeProduct(String product){
        StatusMessageDto result = new StatusMessageDto<>();

        JSONObject productJson = new JSONObject(product);

        ProductDto dto = new ProductDto();

        System.out.println(productRepository.findByProductCode(productJson.getString("productCode")));

        dto.setProductCode(productJson.getString("productCode"));

        if(productRepository.findByProductCode(dto.getProductCode()) != null){
            dto.setProductName(productJson.getString("productName"));
            dto.setDescription(productJson.getString("description"));
            dto.setPrice(productJson.getDouble("price"));
            dto.setStock(productJson.getInt("stock"));
            dto.setIsAvailable(productJson.getInt("isAvailable"));

            result.setMessage(productService.editProductFromTopic(dto));

            System.out.println(" ========================================================= " );
            System.out.println("\t Product data : " + product);
            System.out.println("\t " + result.getMessage());
            System.out.println(" ========================================================= ");
        }
        else{
            dto.setProductName(productJson.getString("productName"));
            dto.setDescription(productJson.getString("description"));
            dto.setPrice(productJson.getDouble("price"));
            dto.setStock(productJson.getInt("stock"));
            dto.setIsAvailable(productJson.getInt("isAvailable"));

            result.setMessage(productService.addProductFromTopic(dto));

            System.out.println(" ========================================================= " );
            System.out.println("\t Product data : " + product);
            System.out.println("\t " + result.getMessage());
            System.out.println(" ========================================================= ");
        }

    }
}
