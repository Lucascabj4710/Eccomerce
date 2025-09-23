package com.eccomerce.orderProductDetail;

import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.mail.EmailService;
import com.eccomerce.order.Order;
import com.eccomerce.order.OrderStatus;
import com.eccomerce.product.exception.ProductNotFoundException;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.product.entity.Product;
import com.eccomerce.order.OrderRepository;
import com.eccomerce.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderDetailServiceImpl implements OrderProductDetailService{

    private final OrderProductDetailRepository orderProductDetailRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    public OrderDetailServiceImpl(OrderProductDetailRepository orderProductDetailRepository, ClientRepository clientRepository, OrderRepository orderRepository, ProductRepository productRepository, EmailService emailService) {
        this.orderProductDetailRepository = orderProductDetailRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;
    }

    @Override
    public Map<String, String> createOrderProductDetail(List<OrderProductDetailDto> detailDtoList) {

        String username = getCurrentUsername();
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ClientNotFoundException(""));

        Order order = orderRepository.findByClient(client.getId()).orElse(Order.builder()
                .client(client)
                .orderStatus(OrderStatus.PENDING)
                .build());
        orderRepository.save(order);

        // --- Para armar el mail ---
        StringBuilder emailBody = new StringBuilder();
        double totalCompra = 0.0;

        emailBody.append("Hola ").append(client.getName()).append(",\n\n")
                .append("Hemos recibido tu pedido y se está procesando correctamente.\n\n")
                .append("Detalles de tu compra:\n");

        for (OrderProductDetailDto orderProductDetailDto : detailDtoList) {
            Product product = productRepository.findByName(orderProductDetailDto.getProductName())
                    .orElseThrow(() -> new ClientNotFoundException("El producto no existe"));

            OrderProductDetail orderProductDetail = OrderProductDetail.builder()
                    .product(product)
                    .discount(orderProductDetailDto.getDiscount())
                    .unitPrice(product.getPrice())
                    .quantity(orderProductDetailDto.getQuantity())
                    .build();

            orderProductDetail.setOrder(order);
            orderProductDetailRepository.save(orderProductDetail);

            // Calculo subtotal
            double subtotal = (product.getPrice() - orderProductDetailDto.getDiscount()) * orderProductDetailDto.getQuantity();
            totalCompra += subtotal;

            // Agrego info del producto al mail
            emailBody.append("- ").append(product.getName())
                    .append(" | Cantidad: ").append(orderProductDetailDto.getQuantity())
                    .append(" | Precio unitario: $").append(product.getPrice())
                    .append(" | Descuento: $").append(orderProductDetailDto.getDiscount())
                    .append(" | Subtotal: $").append(subtotal)
                    .append("\n");
        }

        emailBody.append("\nTotal de la compra: $").append(totalCompra).append("\n\n")
                .append("¡Gracias por confiar en nosotros!\n")
                .append("El equipo de Paradiise");

        // Envio un solo mail al final
        emailService.sendEmail(
                client.getEmail(),
                "¡Gracias por tu compra!",
                emailBody.toString()
        );

        return Map.of("STATUS", "CREATED COMPLETED");
    }


    @Override
    public List<OrderProductDetailResponse> getAllOrderProductDetail() {

        String username = getCurrentUsername();

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("No existe un cliente con ese username"));

        return orderProductDetailRepository.findAll().stream()
                .map(orderProductDetail -> {
                    return OrderProductDetailResponse.builder()
                            .productName(orderProductDetail.getProduct().getName())
                            .idProduct(orderProductDetail.getProduct().getId())
                            .discount(orderProductDetail.getDiscount())
                            .unitPrice(orderProductDetail.getUnitPrice())
                            .priceFinal(orderProductDetail.getPriceFinal())
                            .idOrder(orderProductDetail.getOrder().getId())
                            .quantity(orderProductDetail.getQuantity())
                            .clientName(client.getName() + " " + client.getLastName())
                            .idClient(client.getId())
                            .build();
                }).toList();
    }

    @Override
    public OrderProductDetail getOrderProductDetailID(Long id) {
        return null;
    }

    @Override
    public Map<String, String> deleteOrderProductDetail(Long id) {

        String username = getCurrentUsername();

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("Cliente no encontrado"));

        if (orderProductDetailRepository.existsById(id)){
            if (orderProductDetailRepository.checkClientOrderProductDetail(client.getId(), id).isPresent()){
                orderProductDetailRepository.deleteById(id);
            } else {
                throw new RuntimeException("No tiene autorizacion para eliminar esta orden");
            }
        } else {
            throw new RuntimeException("No existe una orden con ese ID");
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
