package com.eccomerce.controller;

import com.eccomerce.persistence.dto.response.CartDetailRequestDto;
import com.eccomerce.persistence.entity.CartDetail;
import com.eccomerce.service.CartDetailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartDetail")
public class CartDetailController {

    private final CartDetailServiceImpl cartDetailService;

    public CartDetailController(CartDetailServiceImpl cartDetailService) {
        this.cartDetailService = cartDetailService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createCartDetail(@RequestBody CartDetailRequestDto cartDetailRequestDto){



         return cartDetailService.createCartDetail(cartDetailRequestDto);
    }

}
