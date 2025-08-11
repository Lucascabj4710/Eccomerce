package com.eccomerce.orderProductDetail.controller;

import com.eccomerce.orderProductDetail.OrderProductDetailDto;
import com.eccomerce.orderProductDetail.OrderDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orderDetail")
public class OrderProductDetailController {

    private final OrderDetailServiceImpl orderDetailService;

    public OrderProductDetailController(OrderDetailServiceImpl orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderProductDetailDto orderProductDetail){

        return new ResponseEntity<>(orderDetailService.createOrderProductDetail(orderProductDetail), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id){

        return new ResponseEntity<>(orderDetailService.deleteOrderProductDetail(id), HttpStatus.OK);
    }

}
