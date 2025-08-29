package com.eccomerce.userEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserEntityDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
