package com.eccomerce.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private LocalDateTime timestamp; // momento del error
    private int status;               // código HTTP
    private String message;           // mensaje general
    private Map<String, String> errors; // opcional: errores por campo
}
