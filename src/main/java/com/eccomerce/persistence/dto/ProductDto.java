package com.eccomerce.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class ProductDto {

    private Long idCategory;
    private String name;
    private Float price;
    private String color;
    private String material;
    private String waist;
    private Integer stock;

    public ProductDto() {
    }

    public ProductDto(Long idCategory, String name, Float price, String color, String material, String waist, Integer stock) {
        this.idCategory = idCategory;
        this.name = name;
        this.price = price;
        this.color = color;
        this.material = material;
        this.waist = waist;
        this.stock = stock;
    }


}
