package com.eccomerce.orderProductDetail;

import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.order.Order;
import com.eccomerce.order.OrderStatus;
import com.eccomerce.product.exception.ProductNotFoundException;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.product.entity.Product;
import com.eccomerce.order.OrderRepository;
import com.eccomerce.product.repository.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl implements OrderProductDetailService{

    private final OrderProductDetailRepository orderProductDetailRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailServiceImpl(OrderProductDetailRepository orderProductDetailRepository, ClientRepository clientRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderProductDetailRepository = orderProductDetailRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Map<String, String> createOrderProductDetail(OrderProductDetailDto orderProductDetailDto) {

        Product product = productRepository.findById(orderProductDetailDto.getIdProduct()).orElseThrow(()-> new ProductNotFoundException(""));

        OrderProductDetail orderProductDetail = OrderProductDetail.builder()
                .product(product)
                .discount(orderProductDetailDto.getDiscount())
                .unitPrice(product.getPrice())
                .quantity(orderProductDetailDto.getQuantity())
                .build();

//        String username = getCurrentUsername();
        String username = "lucas";
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException(""));

        Order order = orderRepository.findByClient(client.getId()).orElse(Order.builder()
                        .client(client)
                        .orderStatus(OrderStatus.PENDING)
                        .build());
        orderRepository.save(order);

        orderProductDetail.setOrder(order);
        orderProductDetailRepository.save(orderProductDetail);

        return Map.of("STATUS", "CREATED COMPLETED");
    }

    @Override
    public List<OrderProductDetail> getAllOrderProductDetail() {
        return List.of();
    }

    @Override
    public OrderProductDetail getOrderProductDetailID(Long id) {
        return null;
    }

    @Override
    public Map<String, String> deleteOrderProductDetail(Long id) {
//        String username = getCurrentUsername();
        String username = "lucas";

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("Cliente no encontrado"));

        if (orderProductDetailRepository.existsById(id)){
            if (orderProductDetailRepository.checkClientOrderProductDetail(client.getId(), id).isPresent()){
                orderProductDetailRepository.deleteById(id);
            } else {
                throw new RuntimeException("");
            }
        } else {
            throw new RuntimeException("");
        }

        return Map.of("STATUS", "DELETED");
    }

    @Override
    public Map<String, String> updateOrderProductDetail(Long id, OrderProductDetail orderProductDetail) {
        return Map.of();
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
