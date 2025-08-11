package com.eccomerce.address;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String zipCode;
    private String street;
    private String numberStreet;
    private String department;
    private String floor;
    private String game;
    private String province;
}
