package com.eccomerce.cartDetail.service;

import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.cartDetail.dto.CartDetailRequestDto;
import java.util.List;
import java.util.Map;

public interface CartDetailService {

    Map<String, String> createCartDetail(CartDetailRequestDto cartDetailRequestDto);
    List<CartDetailResponseDto> getCartDetail();
    CartDetailResponseDto getCartDetailID(Long id);
    Map<String, String> deleteCartDetail(Long id);
    Map<String, String> updateCartDetail(Long id, Long quantity);

}
