package com.eccomerce.exception;

import com.eccomerce.cart.exception.CartNotFoundException;
import com.eccomerce.cartDetail.exception.CartDetailNotFoundException;
import com.eccomerce.category.exception.CategoryNotFoundException;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.product.exception.InvalidStockException;
import com.eccomerce.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String, String> errorss = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .forEach(error -> errorss.put(error.getField(), error.getDefaultMessage()));


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación en los campos enviados")
                .errors(errorss)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handlerCartNotFoundException(CartNotFoundException cartNotFoundException){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("El carrito no fue encontrado")
                .errors(null)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(CartDetailNotFoundException.class)
    public ResponseEntity<String> handleCartDetailNotFoundException(CartDetailNotFoundException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<String> handleInvalidStockException(InvalidStockException exception){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }



}
