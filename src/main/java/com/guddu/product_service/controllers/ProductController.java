package com.guddu.product_service.controllers;


import com.guddu.product_service.dtos.ProductRequestDto;
import com.guddu.product_service.dtos.ProductResponseDto;
import com.guddu.product_service.exceptions.ProductNotFoundException;
import com.guddu.product_service.models.Product;
import com.guddu.product_service.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductDetails(@PathVariable("id") int productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
//        return modelMapper.map(product, ProductResponseDto.class);
        return convertProductToProductResponseDto(product);
    }


    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            productResponseDtos.add(convertProductToProductResponseDto(product));
        }
        return productResponseDtos;
    }


    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> addNewProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
//        return convertProductToProductResponseDto(product);
//        return ResponseEntity.status(HttpStatus.CREATED).body(convertProductToProductResponseDto(product));
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/products/{id}")
    public ProductResponseDto deleteProduct(@PathVariable("id") int productId) throws ProductNotFoundException {
        Product product = productService.deleteProduct(productId);

//        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        return convertProductToProductResponseDto(product);
    }


    //Method to convert product to productResponseDto
    public ProductResponseDto convertProductToProductResponseDto(Product product) {
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }


//    //Add Exception Handler
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(productNotFoundException.getMessage());
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }
}
