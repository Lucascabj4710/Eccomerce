package com.eccomerce.order.service;

import com.eccomerce.cart.Cart;
import com.eccomerce.cart.CartRepository;
import com.eccomerce.cart.exception.CartNotFoundException;
import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.cartDetail.exception.CartDetailNotFoundException;
import com.eccomerce.cartDetail.repository.CartDetailRepository;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.entity.Client;
import com.eccomerce.order.Order;
import com.eccomerce.order.OrderStatus;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.order.OrderRepository;
import com.eccomerce.orderProductDetail.OrderProductDetail;
import com.eccomerce.orderProductDetail.OrderProductDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderProductDetailRepository orderProductDetailRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, OrderProductDetailRepository orderProductDetailRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderProductDetailRepository = orderProductDetailRepository;
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

    @Override
    @Transactional
    public Map<String, String> buyCart() {

        String username = "lucas";
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("El cliente no ha sido encontrado"));

        Cart cart = cartRepository.findByClientId(client.getId()).orElseThrow(()-> new CartNotFoundException("Error carrito no existente"));

        List<CartDetail> cartDetails = cartDetailRepository.findByCartDetailID(cart.getId()).orElseThrow(()-> new CartDetailNotFoundException("CartDetail no existe"));

        log.info("Error aca antes del order builder");

        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .client(client)
                .orderStatus(OrderStatus.PENDING)
                .build();
        log.info("Error aca antes de guardar el order");

        float priceFinalOrder = (float) cartDetails.stream()
                .mapToDouble(cd -> cd.getUnitPrice() * cd.getQuantity())
                .sum();

        order.setPriceFinal(priceFinalOrder);

        orderRepository.save(order);

        log.info("Error aca antes de obterner el id de order");

        List<OrderProductDetail> orderProductDetails = cartDetails.stream()
                .map(cartDetail -> {
                    return OrderProductDetail.builder()
                            .product(cartDetail.getProduct())
                            .unitPrice(cartDetail.getUnitPrice())
                            .quantity(cartDetail.getQuantity())
                            .discount(0F)
                            .order(order)
                            .build();
                }).toList();

        log.info("Error aca dsp de obtener la lista de orderProductDetail");

        orderProductDetailRepository.saveAll(orderProductDetails);

        cartDetailRepository.deleteAll(cartDetails);

        return Map.of("COMPLETED", "OPA");
    }


    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
