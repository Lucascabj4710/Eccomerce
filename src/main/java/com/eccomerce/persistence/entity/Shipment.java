package com.eccomerce.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShippingType shippingType;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;

    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;
}
