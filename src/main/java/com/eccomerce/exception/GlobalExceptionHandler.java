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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------
    // VALIDACIONES @Valid
    // -------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación en los campos enviados")
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    // -------------------------
    // NOT FOUND
    // -------------------------
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handleCartNotFoundException(CartNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartDetailNotFoundException.class)
    public ResponseEntity<ApiError> handleCartDetailNotFoundException(CartDetailNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiError> handleClientNotFoundException(ClientNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    // -------------------------
    // BAD REQUEST / STOCK
    // -------------------------
    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<ApiError> handleInvalidStockException(InvalidStockException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    // -------------------------
    // EXCEPCIÓN GENÉRICA
    // -------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {

        ex.printStackTrace(); // DEBUG

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Ha ocurrido un error inesperado. Contacta al soporte.")
                .errors(Map.of("exception", ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }


    // -------------------------
    // MÉTODO UTIL PARA NO REPETIR CÓDIGO
    // -------------------------
    private ResponseEntity<ApiError> buildResponse(String message, HttpStatus status) {
        ApiError apiError = ApiError.builder()
                .status(status.value())
                .message(message)
                .errors(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(apiError);
    }
}
