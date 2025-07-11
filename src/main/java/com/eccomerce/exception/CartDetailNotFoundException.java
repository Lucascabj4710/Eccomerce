package com.eccomerce.exception;

public class CartDetailNotFoundException extends RuntimeException {

    public CartDetailNotFoundException (String message){

        super(message);
    }
}
