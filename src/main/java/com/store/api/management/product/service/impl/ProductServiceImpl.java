package com.store.api.management.product.service.impl;

import com.store.api.management.product.model.ProductDTO;
import com.store.api.management.product.model.domain.Product;
import com.store.api.management.product.model.mapper.ProductMapper;
import com.store.api.management.product.repository.ProductRepository;
import com.store.api.management.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {
        return productMapper.productListToProductDTOList(productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {

        return productRepository.findById(id)
                                .map(productMapper::productToProductDTO);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        return productMapper.productToProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findByUserId(Long userId) {
        return productMapper.productListToProductDTOList(productRepository.findByUserId(userId));
    }
}
