package com.eccomerce.cartDetail.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CartDetailRequestDto {

    private Long product;
    private Long quantity;
}
