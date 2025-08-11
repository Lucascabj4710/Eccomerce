package com.eccomerce.order.service;

import java.util.Map;

public interface OrderService {

    public Map<String, String> changeStateOrder(String status, Long idOrder);

}
