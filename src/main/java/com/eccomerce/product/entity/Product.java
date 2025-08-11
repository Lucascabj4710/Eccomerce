package com.eccomerce.product.entity;

import com.eccomerce.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private Float price;
    private String color;
    private String material;
    private String waist;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @Column(nullable = false)
    private Integer stock;
}
