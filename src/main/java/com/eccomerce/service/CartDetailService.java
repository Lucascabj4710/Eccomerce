package com.eccomerce.service;

import com.eccomerce.persistence.dto.response.CartDetailResponseDto;
import com.eccomerce.persistence.dto.response.CartDetailRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CartDetailService {

    ResponseEntity<?> createCartDetail(CartDetailRequestDto cartDetailRequestDto);
    List<CartDetailResponseDto> getCartDetail();
    CartDetailResponseDto getCartDetailID(Long id);
    Map<String, String> deleteCartDetail(Long id);
    Map<String, String> updateCartDetail(Long id, CartDetailRequestDto cartDetailRequestDto);

}
