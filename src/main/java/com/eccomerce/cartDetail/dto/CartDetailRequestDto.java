package com.eccomerce.cartDetail.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CartDetailRequestDto {

    @Min(value = 1, message = "El ID no puede ser menor a 1")
    @NotNull(message = "No puede estar vacio y/o null el Id de producto")
    private Long product;
    @Min(value = 1, message = "El ID no puede ser menor a 1")
    @NotNull(message = "No puede estar vacio y/o null la cantidad")
    private Long quantity;
}
