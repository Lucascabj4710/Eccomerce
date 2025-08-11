package com.eccomerce.shipment;

import com.eccomerce.client.entity.Client;
import com.eccomerce.order.Order;
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order", referencedColumnName = "id")
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    private Client client;

    private State state;

    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;

    private ShippingType shippingType;


}
