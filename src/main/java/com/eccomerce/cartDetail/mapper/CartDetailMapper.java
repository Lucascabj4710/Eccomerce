package com.eccomerce.cartDetail.mapper;

import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CartDetailMapper {

    private final ModelMapper modelMapper;

    public CartDetailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDetail convertToCartDetail(CartDetailResponseDto cartDetailResponseDto){
        if(modelMapper.getTypeMap(CartDetailResponseDto.class, CartDetail.class) == null) {
            TypeMap<CartDetailResponseDto, CartDetail> propertyMapper = modelMapper.createTypeMap(CartDetailResponseDto.class, CartDetail.class);
            propertyMapper.addMappings(mapping -> mapping.skip(CartDetail::setProduct));
            propertyMapper.addMappings(mapping -> mapping.skip(CartDetail::setCart));
            propertyMapper.addMapping(CartDetailResponseDto::getQuantity, CartDetail::setQuantity);
            propertyMapper.addMapping(CartDetailResponseDto::getUnitPrice, CartDetail::setUnitPrice);
        }
        return modelMapper.map(cartDetailResponseDto, CartDetail.class);
    }

}
