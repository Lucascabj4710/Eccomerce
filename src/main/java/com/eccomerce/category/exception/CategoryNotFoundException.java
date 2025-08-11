package com.eccomerce.category.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException (String message){
        super(message);
    }
}
