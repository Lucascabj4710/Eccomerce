package com.eccomerce.order;

import com.eccomerce.client.entity.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<OrderProductDetail> orderProductDetail;


    @PrePersist
    private void generateDate(){
        this.orderDate = LocalDate.now();
    }
}
