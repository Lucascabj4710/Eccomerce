package com.eccomerce.order.service;

import com.eccomerce.cartDetail.entity.CartDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderService {

    public Map<String, String> changeStateOrder(String status, Long idOrder);

    public Map<String, String> buyCart();

}
