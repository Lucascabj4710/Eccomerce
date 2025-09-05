package com.eccomerce.client.dto;

import com.eccomerce.userEntity.UserEntityDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ClientDto {

    private Long idAddress;

    @NotEmpty(message = "El nombre no puede estar vacio o null")
    private String name;
    @NotEmpty(message = "El nombre no puede estar vacio o null")
    private String lastname;
    @NotBlank(message = "El nombre no puede estar vacio o null")
    private String phoneNumber;
    @NotBlank(message = "El nombre no puede estar vacio o null")
    @Pattern(regexp = "\\d+", message = "El DNI debe contener solo números")
    private String dni;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El campo debe tener el formato email")
    private String email;
    @Valid
    private UserEntityDto userEntityDto;
    
}
