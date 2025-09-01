package com.eccomerce.material;

import jakarta.validation.constraints.NotBlank;

public class MaterialDto {

    @NotBlank(message = "El material no puede ser vacio")
    private String name;

}
