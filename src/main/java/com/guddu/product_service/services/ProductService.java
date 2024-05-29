package com.guddu.product_service.services;

import com.guddu.product_service.dtos.FakeStoreDto;
import com.guddu.product_service.dtos.ProductResponseDto;
import com.guddu.product_service.exceptions.ProductNotFoundException;
import com.guddu.product_service.models.Product;

import java.util.List;

public interface ProductService {

    public Product getSingleProduct(int productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();

    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    );

    public Product deleteProduct(int productId) throws ProductNotFoundException;

}
