package com.eccomerce.order.service;

import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.entity.Client;
import com.eccomerce.order.Order;
import com.eccomerce.order.OrderStatus;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.order.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Map<String, String> changeStateOrder(String status, Long idOrder) {

        List<OrderStatus> statusList = List.of(OrderStatus.values());

        OrderStatus orderStatus = statusList.stream()
                .filter(orderStatus1 -> status.toUpperCase().equals(orderStatus1.name()))
                .findFirst().orElseThrow(()-> new RuntimeException(""));

//        String username = getUsername();
        String username = "lucas";

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("El cliente no ha sido encontrado"));

        Order order = orderRepository.findByClientAndOrderID(client.getId(), idOrder).orElseThrow();

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        return Map.of();
    }

    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
