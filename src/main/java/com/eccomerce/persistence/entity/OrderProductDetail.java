package com.eccomerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Product product;
    private Order order;

    @Column(name = "unit_price")
    private Float unitPrice;
    private Integer quantity;
    private Float discount;
    @Column(name = "price_final")
    private Float priceFinal;

    @PrePersist
    @PreUpdate
    private void calculateFinalPrice(){
        this.priceFinal = this.unitPrice * (this.quantity - this.discount);
    }

}
