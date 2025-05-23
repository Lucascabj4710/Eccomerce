package com.eccomerce.persistence.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddresDto {

    private String zipCode;
    private String street;
    private String numberStreet;
    private String department;
    private String floor;
    private String game;
    private String province;
}
