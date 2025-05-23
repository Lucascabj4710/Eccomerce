package com.eccomerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Client.class)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderProductDetail> orderProductDetail;

    @Enumerated(EnumType.STRING)
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
