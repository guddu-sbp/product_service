package com.guddu.product_service.services;

import com.guddu.product_service.dtos.FakeStoreDto;
import com.guddu.product_service.dtos.ProductResponseDto;
import com.guddu.product_service.models.Product;

public interface ProductService {

    public ProductResponseDto getSingleProduct(int productId);

    public ProductResponseDto addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price
    );
}
