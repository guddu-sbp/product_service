package com.guddu.product_service.services;

import com.guddu.product_service.dtos.FakeStoreDto;
import com.guddu.product_service.dtos.ProductResponseDto;
import com.guddu.product_service.exceptions.ProductNotFoundException;
import com.guddu.product_service.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate = new RestTemplate();

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {

        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId, FakeStoreDto.class
        );

        //Exception Handling
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException("Product with id " + productId + " doesn't exist. Try a id" +
                    " with product id less than 21.");
        }

        return fakeStoreDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreDto[] fakeStoreDto = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreDto[].class);

        //convert all fakeStoreDto to product object
        List<Product> products = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto1 : fakeStoreDto) {
            products.add(fakeStoreDto1.toProduct());
        }
        return products;
    }

    @Override
    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price) {

        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setImage(imageUrl);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setPrice(price);

        FakeStoreDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products", fakeStoreDto, FakeStoreDto.class
        );
        return fakeStoreDto.toProduct();
    }

    @Override
    public Product deleteProduct(int productId) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreDto.class);
        if (fakeStoreDto == null) {
            throw new ProductNotFoundException("Product with id " + productId + " doesn't exist.");
        }
        restTemplate.delete("https://fakestoreapi.com/products/" + productId);
        return fakeStoreDto.toProduct();
    }
}
