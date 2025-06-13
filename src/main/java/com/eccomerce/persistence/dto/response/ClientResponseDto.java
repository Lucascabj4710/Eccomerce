package com.eccomerce.persistence.dto.response;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientResponseDto {

    private String name;
    private String lastname;
    private String phoneNumber;
    private String dni;
    private String email;

}
