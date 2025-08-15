package com.eccomerce.orderProductDetail;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OrderProductDetailDto {

    private Long idProduct;
    private Long quantity;
    private Float discount;

}
