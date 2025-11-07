package com.eccomerce.order;

import com.eccomerce.client.entity.Client;
import com.eccomerce.order.OrderStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Client client;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private Float priceFinal;
}

