package com.eccomerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ProductDto {

    private String imageUrl;
    private Long idCategory;
    private String name;
    private Float price;
    private String color;
    private String material;
    private String waist;
    private Integer stock;
    private String description;



}
