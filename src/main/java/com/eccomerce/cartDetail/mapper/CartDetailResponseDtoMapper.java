package com.eccomerce.cartDetail.mapper;

import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.product.mapper.ProductDtoMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CartDetailResponseDtoMapper {

    private final ModelMapper modelMapper;
    private final ProductDtoMapper productDtoMapper;

    public CartDetailResponseDtoMapper(ModelMapper modelMapper, ProductDtoMapper productDtoMapper) {
        this.modelMapper = modelMapper;
        this.productDtoMapper = productDtoMapper;
    }

    public CartDetailResponseDto convertToCartDetailResponseDto(CartDetail cartDetail){
        if(modelMapper.getTypeMap(CartDetail.class, CartDetailResponseDto.class) == null){
            TypeMap<CartDetail, CartDetailResponseDto> propertyMapper = modelMapper.createTypeMap(CartDetail.class, CartDetailResponseDto.class);
            propertyMapper.addMappings(mapping ->
                    mapping.skip(CartDetailResponseDto::setProductResponseDto));
            propertyMapper.addMapping(CartDetail::getUnitPrice, CartDetailResponseDto::setUnitPrice);
            propertyMapper.addMapping(CartDetail::getQuantity, CartDetailResponseDto::setQuantity);
        }
        CartDetailResponseDto cartDetailResponseDto =modelMapper.map(cartDetail, CartDetailResponseDto.class);

        cartDetailResponseDto.setProductResponseDto(productDtoMapper.converterToProductResponseDto(cartDetail.getProduct()));

        return cartDetailResponseDto;
    }
}
