package com.eccomerce.product.mapper;

import com.eccomerce.product.dto.ProductResponseDto;
import com.eccomerce.product.entity.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {

    private final ModelMapper modelMapper;

    public ProductDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductResponseDto converterToProductResponseDto(Product product) {
        // Mapeo básico
        ProductResponseDto dto = modelMapper.map(product, ProductResponseDto.class);

        // Null-safe para category
        if (product.getCategory() != null) {
            dto.setCategoryDesc(product.getCategory().getName());
        }

        // Null-safe para enum
        if (product.getIsEnabled() != null) {
            dto.setIsEnabled(product.getIsEnabled().name());
        }

        return dto;
    }
}

