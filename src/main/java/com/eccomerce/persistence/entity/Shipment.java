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
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShippingType shippingType;

    @OneToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    private Order order;

    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;
}
