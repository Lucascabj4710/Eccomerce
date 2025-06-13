package com.eccomerce.service;

import com.eccomerce.persistence.dto.response.CartDetailRequestDto;
import com.eccomerce.persistence.dto.response.CartDetailResponseDto;
import com.eccomerce.persistence.repository.CartDetailRepository;
import com.eccomerce.persistence.repository.CartRepository;

import java.util.List;
import java.util.Map;

public class CartDetailServiceImpl implements CartDetailService{

    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;

    public CartDetailServiceImpl(CartDetailRepository cartDetailRepository, CartRepository cartRepository) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Map<String, String> createCartDetail(CartDetailRequestDto cartDetailRequestDto) {



        return Map.of();
    }

    @Override
    public List<CartDetailResponseDto> getCartDetail() {
        return List.of();
    }

    @Override
    public CartDetailResponseDto getCartDetailID(Long id) {
        return null;
    }

    @Override
    public Map<String, String> deleteCartDetail(Long id) {
        return Map.of();
    }

    @Override
    public Map<String, String> updateCartDetail(Long id, CartDetailRequestDto cartDetailRequestDto) {
        return Map.of();
    }
}
