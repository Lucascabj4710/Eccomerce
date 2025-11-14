package com.eccomerce.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    @NotBlank(message = "El código postal es obligatorio")
    private String zipCode;  // Sin límite de dígitos

    @NotBlank(message = "La calle es obligatoria")
    @Size(min = 3, max = 60, message = "La calle debe tener entre 3 y 60 caracteres")
    private String street;

    @NotBlank(message = "El número es obligatorio")
    @Pattern(regexp = "\\d+", message = "El número debe ser solo números")
    private String numberStreet; // Números sin límite

    @Size(max = 50, message = "El departamento no puede superar los 50 caracteres")
    private String department;

    @Size(max = 30, message = "El piso no puede superar los 30 caracteres")
    private String floor; // Ahora hasta 30 caract.

    @Size(max = 50, message = "El barrio o localidad no puede superar los 50 caracteres")
    private String game;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(min = 3, max = 40, message = "La provincia debe tener entre 3 y 40 caracteres")
    private String province;
}
