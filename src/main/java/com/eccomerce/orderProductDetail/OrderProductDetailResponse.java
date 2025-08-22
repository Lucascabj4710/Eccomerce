package com.eccomerce.orderProductDetail;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDetailResponse {

    private Long idClient;
    private String clientName;
    private Long idOrder;
    private Long idProduct;
    private String productName;
    private Float unitPrice;
    private Long quantity;
    private Float discount;
    private Float priceFinal;

}
