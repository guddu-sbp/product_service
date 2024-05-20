package com.guddu.product_service.controllers;


import com.guddu.product_service.ProductServiceApplication;
import com.guddu.product_service.dtos.ProductRequestDto;
import com.guddu.product_service.dtos.ProductResponseDto;
import com.guddu.product_service.models.Product;
import com.guddu.product_service.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductServiceApplication productServiceApplication;
    private ProductService productService;

    public ProductController(ProductService productService, ProductServiceApplication productServiceApplication) {
        this.productService = productService;
        this.productServiceApplication = productServiceApplication;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductDetails(@PathVariable("id") int productId) {
        return productService.getSingleProduct(productId);
    }

    @PostMapping("/products")
    public ProductResponseDto addNewProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
    }
}
