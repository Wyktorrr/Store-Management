package com.store.api.management.product.model.mapper;

import com.store.api.management.product.model.ProductDTO;
import com.store.api.management.product.model.domain.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> productListToProductDTOList(List<Product> products);

    List<Product> productDTOListToProductList(List<ProductDTO> productDTOs);
}
