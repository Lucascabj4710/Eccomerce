package com.eccomerce.cartDetail.dto;

import com.eccomerce.product.dto.ProductResponseDto;
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

}
