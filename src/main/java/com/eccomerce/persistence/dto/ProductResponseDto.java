package com.eccomerce.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ProductResponseDto {

    private String name;
    private String categoryDesc;
    private Float price;
    private String color;
    private String material;
    private String waist;

    public ProductResponseDto() {
    }

    public ProductResponseDto(String name, String categoryDesc, Float price, String color, String material, String waist) {
        this.name = name;
        this.categoryDesc = categoryDesc;
        this.price = price;
        this.color = color;
        this.material = material;
        this.waist = waist;
    }
}


