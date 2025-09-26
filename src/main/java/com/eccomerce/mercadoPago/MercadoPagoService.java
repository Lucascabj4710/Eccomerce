package com.eccomerce.mercadoPago;

import com.eccomerce.cartDetail.dto.CartDetailRequestDto;
import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.List;

public interface MercadoPagoService {

    public String buyMP(List<CartDetailResponseDto> cartDetailResponseDtos) throws MPException, MPApiException;


}
