package com.eccomerce.product.mapper;

import com.eccomerce.product.dto.ProductDto;
import com.eccomerce.product.entity.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product converterToProduct(ProductDto productDto){

        if (modelMapper.getTypeMap(ProductDto.class, Product.class) == null) {
            TypeMap<ProductDto, Product> propertyMapper = modelMapper.createTypeMap(ProductDto.class, Product.class);
            propertyMapper.addMappings(mapper -> mapper.skip(Product::setId)); // Ignorar id
            propertyMapper.addMappings(mapper -> mapper.skip(Product::setImageUrl)); // Ignorar url
            propertyMapper.addMapping(ProductDto::getName, Product::setName);
            propertyMapper.addMapping(ProductDto::getPrice, Product::setPrice);
            propertyMapper.addMapping(ProductDto::getColor, Product::setColor);
            propertyMapper.addMapping(ProductDto::getMaterial, Product::setMaterial);
            propertyMapper.addMapping(ProductDto::getWaist, Product::setWaist);
        }

        return modelMapper.map(productDto, Product.class);
    }


}
