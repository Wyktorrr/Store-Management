package com.store.api.management.product.service;

import com.store.api.management.product.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(Long id);

    ProductDTO save(ProductDTO productDTO);

    void deleteById(Long id);

    List<ProductDTO> findByUserId(Long userId);

    ProductDTO updateProductPrice(Long productId, double newPrice);
}
