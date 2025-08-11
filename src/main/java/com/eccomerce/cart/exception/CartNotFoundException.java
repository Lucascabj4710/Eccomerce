package com.eccomerce.cart.exception;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String message){
        super(message);
    }

}
