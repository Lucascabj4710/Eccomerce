package com.eccomerce.persistence.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ClientDto {

    private Long idAddress;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String dni;
    private UserDto userDto;
    
}
