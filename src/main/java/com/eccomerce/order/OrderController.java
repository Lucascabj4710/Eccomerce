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

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){

        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }


    @PatchMapping("patch/{idOrder}/{status}/{clientId}")
    public ResponseEntity<?> modifyStatus(@PathVariable Long idOrder, @PathVariable String status, @PathVariable Long clientId){

        return new ResponseEntity<>(orderService.changeStateOrder(status, idOrder, clientId), HttpStatus.OK);
    }


    @GetMapping("buyCart")
    public ResponseEntity<?> buyCart(){

        return new ResponseEntity<>(orderService.buyCart(), HttpStatus.OK);
    }

}
