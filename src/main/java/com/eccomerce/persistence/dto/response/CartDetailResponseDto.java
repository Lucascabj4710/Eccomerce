package com.eccomerce.persistence.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponseDto {

    private ProductResponseDto productResponseDto;
    private Long quantity;
    private Float unitPrice;
    private Float totalPrice;

}
