package com.eccomerce.cartDetail.controller;

import com.eccomerce.cartDetail.service.CartDetailServiceImpl;
import com.eccomerce.cartDetail.dto.CartDetailRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartDetail")
public class CartDetailController {

    private final CartDetailServiceImpl cartDetailService;

    public CartDetailController(CartDetailServiceImpl cartDetailService) {
        this.cartDetailService = cartDetailService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createCartDetail(@RequestBody @Valid CartDetailRequestDto cartDetailRequestDto){

         return ResponseEntity.status(HttpStatus.CREATED).body(cartDetailService.createCartDetail(cartDetailRequestDto));
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllCartDetail(){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetailService.getCartDetail());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getAllCartDetail(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetailService.getCartDetailID(id));
    }

    @DeleteMapping("delete/{id}/{productName}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable Long id, @PathVariable String productName){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetailService.deleteCartDetail(id, productName));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCartDetail(@PathVariable Long id, @RequestBody Long quantity){

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetailService.updateCartDetail(id, quantity));
    }

}
