package com.eccomerce.orderProductDetail;

import com.eccomerce.order.Order;
import com.eccomerce.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @Column(name = "unit_price")
    private Float unitPrice;

    private Long quantity;

    private Float discount;
    @Column(name = "price_final")
    private Float priceFinal;

    @PrePersist
    @PreUpdate
    private void calculateFinalPrice(){
        this.priceFinal = this.unitPrice * this.quantity - this.discount;
    }

}
