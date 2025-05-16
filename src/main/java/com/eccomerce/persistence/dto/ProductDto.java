package com.eccomerce.persistence.dto;

public class ProductDto {
    private Long idCategory;
    private String name;
    private Float price;
    private String color;
    private String material;
    private String waist;

    public ProductDto() {
    }

    public ProductDto(Long idCategory, String name, Float price, String color, String material, String waist) {
        this.idCategory = idCategory;
        this.name = name;
        this.price = price;
        this.color = color;
        this.material = material;
        this.waist = waist;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }
}
