package com.store.api.management.product.model.mapper;

import com.store.api.management.product.model.ProductDTO;
import com.store.api.management.product.model.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //@Mapping(source = "user.id", target = "userId")
    ProductDTO productToProductDTO(Product product);

    //@Mapping(source = "userId", target = "user.id")
    Product productDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> productListToProductDTOList(List<Product> products);

    List<Product> productDTOListToProductList(List<ProductDTO> productDTOs);
}
