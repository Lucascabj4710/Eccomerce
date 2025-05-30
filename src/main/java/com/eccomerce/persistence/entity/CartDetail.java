package com.eccomerce.persistence.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CartDetail {

    private Long id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    private Product product;
    private Long quantity;
    private Float unitPrice;
}
