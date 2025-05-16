package com.eccomerce.persistence.dto;

public class ProductResponseDto {

    private String name;
    private CategoryDto categoryDto;
    private Float price;
    private String color;
    private String material;
    private String waist;

    public ProductResponseDto() {
    }

    public ProductResponseDto(String name, CategoryDto categoryDto, Float price, String color, String material, String waist) {
        this.name = name;
        this.categoryDto = categoryDto;
        this.price = price;
        this.color = color;
        this.material = material;
        this.waist = waist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
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

