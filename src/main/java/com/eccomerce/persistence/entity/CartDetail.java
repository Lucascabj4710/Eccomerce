package com.eccomerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder @Setter @Getter
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    private Product product;
    private Long quantity;
    private Float unitPrice;
    private Float totalPrice;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice(){
        this.totalPrice = this.unitPrice * this.quantity;
    }


}
