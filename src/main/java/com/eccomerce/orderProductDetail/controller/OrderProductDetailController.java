package com.eccomerce.orderProductDetail.controller;

import com.eccomerce.orderProductDetail.OrderProductDetailDto;
import com.eccomerce.orderProductDetail.OrderDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderDetail")
public class OrderProductDetailController {

    private final OrderDetailServiceImpl orderDetailService;

    public OrderProductDetailController(OrderDetailServiceImpl orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createOrderDetail(@RequestBody List<OrderProductDetailDto> orderProductDetail){

        return new ResponseEntity<>(orderDetailService.createOrderProductDetail(orderProductDetail), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getOrderDetail(){

        return ResponseEntity.status(HttpStatus.OK).body(orderDetailService.getAllOrderProductDetail());
    }



    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id){

        return new ResponseEntity<>(orderDetailService.deleteOrderProductDetail(id), HttpStatus.OK);
    }


}
