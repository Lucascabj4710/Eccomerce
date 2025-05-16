package com.eccomerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Client.class)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "order_product_detail", nullable = false)
    private OrderProductDetail orderProductDetail;

    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @OneToOne
    private Shipment shipment;

    @PrePersist
    private void generateDate(){
        this.orderDate = LocalDate.now();
    }
}
