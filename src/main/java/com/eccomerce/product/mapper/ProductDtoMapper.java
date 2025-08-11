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

    public ProductResponseDto converterToProductResponseDto(Product product){

        TypeMap<Product, ProductResponseDto> propertyMapper;
        if (modelMapper.getTypeMap(Product.class, ProductResponseDto.class) == null) {
            propertyMapper = modelMapper.createTypeMap(Product.class, ProductResponseDto.class);
            propertyMapper.addMappings(mapper ->
                    mapper.map(src -> src.getCategory().getName(),  ProductResponseDto::setCategoryDesc)
            );
        } else {
            propertyMapper = modelMapper.getTypeMap(Product.class, ProductResponseDto.class);
        }

        return propertyMapper.map(product);
    }
}
