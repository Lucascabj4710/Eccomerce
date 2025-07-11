package com.eccomerce.mapper;

import com.eccomerce.persistence.dto.response.CartDetailResponseDto;
import com.eccomerce.persistence.entity.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartDetailMapper {

    CartDetailMapper INSTANCE = Mappers.getMapper(CartDetailMapper.class);

    @Mapping(target = "productResponseDto", ignore = true)
    CartDetailResponseDto CartDetailToCartDetailResponseDto(CartDetail cartDetail);

}
