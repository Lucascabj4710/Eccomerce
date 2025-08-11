package com.eccomerce.product.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponseDto {

    private String name;
    private String imageUrl;
    private String categoryDesc;
    private Float price;
    private String color;
    private String material;
    private String waist;

}


