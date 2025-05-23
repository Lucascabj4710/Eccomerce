package com.eccomerce.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private Boolean isEnabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
}
