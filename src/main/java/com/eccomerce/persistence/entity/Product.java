package com.eccomerce.persistence.entity;

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

    @ManyToOne
    private Category category;

    private String name;

    @Column(nullable = false)
    private Float price;
    private String color;
    private String material;
    private String waist;

    @Column(nullable = false)
    private String stock;
}
