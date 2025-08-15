package com.eccomerce.order;

import com.eccomerce.order.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("patch/{idOrder}/{status}")
    public ResponseEntity<?> modifyStatus(@PathVariable Long idOrder, @PathVariable String status){

        return new ResponseEntity<>(orderService.changeStateOrder(status, idOrder), HttpStatus.OK);
    }

    @PostMapping("buyCart")
    public ResponseEntity<?> buyCart(){

        return new ResponseEntity<>(orderService.buyCart(), HttpStatus.OK);
    }

}
