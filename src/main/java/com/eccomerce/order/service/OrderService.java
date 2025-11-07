package com.eccomerce.order.service;

import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.order.OrderDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderService {

    public List<OrderDto> getOrders();

    public Map<String, String> changeStateOrder(String status, Long idOrder, Long clienteId);

    public Map<String, String> buyCart();

}
