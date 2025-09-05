package com.eccomerce.order;

public class OrderStatusNotFound extends RuntimeException{

    public OrderStatusNotFound(String message){
        super(message);
    }
}
