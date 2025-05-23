package com.eccomerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "id_cart"), inverseJoinColumns = @JoinColumn(name = "id_product"))
    private Set<Product> products;

    // Verificar en el futuro un posible cambio a enum en tipo de medio de pago
    private String paymentMethod;


}
