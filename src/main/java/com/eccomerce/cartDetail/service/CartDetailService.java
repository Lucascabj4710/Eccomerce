package com.eccomerce.cartDetail.service;

import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.cartDetail.dto.CartDetailRequestDto;

import java.util.List;

public interface CartDetailService {

    void createCartDetail(CartDetailRequestDto cartDetailRequestDto);
    List<CartDetailResponseDto> getCartDetail();
    CartDetailResponseDto getCartDetailID(Long id);
    void deleteCartDetail(Long idCart, String productName);
    void updateCartDetail(Long id, Long quantity);

}
