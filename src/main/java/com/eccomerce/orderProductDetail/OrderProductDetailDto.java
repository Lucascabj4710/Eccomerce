package com.eccomerce.orderProductDetail;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OrderProductDetailDto {

    private String productName;
    private Long quantity;
    private Float discount;

}
