package com.eccomerce.service;

import com.eccomerce.persistence.dto.response.CartDetailRequestDto;
import com.eccomerce.persistence.dto.response.CartDetailResponseDto;
import com.eccomerce.persistence.entity.Cart;
import com.eccomerce.persistence.entity.CartDetail;
import com.eccomerce.persistence.entity.Client;
import com.eccomerce.persistence.entity.Product;
import com.eccomerce.persistence.repository.CartDetailRepository;
import com.eccomerce.persistence.repository.CartRepository;
import com.eccomerce.persistence.repository.ClientRepository;
import com.eccomerce.persistence.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class CartDetailServiceImpl implements CartDetailService{

    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public CartDetailServiceImpl(CartDetailRepository cartDetailRepository, CartRepository cartRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createCartDetail(CartDetailRequestDto cartDetailRequestDto) {

        Product product = productRepository.findById(cartDetailRequestDto.getProduct()).orElseThrow(()-> new NoSuchElementException("El producto no existe"));

        // Obtiene el nombre de usuario del cliente actualmente logueado

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("HOLAAAAA" +username);
        //String username = "lucas";

        // Busca el cliente asociado al USERNAME del usuario logueado
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new NoSuchElementException("Error no existe un cliente con ese ID"));

        // Verifica si el cliente tiene un carrito creado, caso contrario creara uno
        if (cartRepository.findByClientId(client.getId()).isEmpty()){
            Cart cart = Cart.builder()
                    .client(client)
                    .build();
            cartRepository.save(cart);
        }


        Cart cart = cartRepository.findByClientId(client.getId()).orElseThrow(()-> new NoSuchElementException("El carrito no existe"));

        if(cartDetailRepository.verificarCarritoDetalle(cart.getId(), product.getId()).isPresent()){

            cartDetailRepository.subirCantidad(cartDetailRequestDto.getQuantity(), cart.getId(), product.getId());
        } else {

            CartDetail cartDetail = CartDetail.builder()
                    .cart(cart)
                    .unitPrice(product.getPrice())
                    .quantity(cartDetailRequestDto.getQuantity())
                    .product(product)
                    .build();


            cartDetailRepository.save(cartDetail);

            // Codigo temporal para testear descuento de STOCK
            if (product.getStock() == 0 || product.getStock() < cartDetail.getQuantity()) {
                log.error("No se puede actualizar el stock");
                throw new RuntimeException("ERROR");
            } else {
                productRepository.discountProductStock(cartDetail.getQuantity(), product.getId());
            }
        }


        return new ResponseEntity<>("COMPLETED",HttpStatusCode.valueOf(201));
    }

    @Override
    public List<CartDetailResponseDto> getCartDetail() {
        return List.of();
    }

    @Override
    public CartDetailResponseDto getCartDetailID(Long id) {
        return null;
    }

    @Override
    public Map<String, String> deleteCartDetail(Long id) {
        return Map.of();
    }

    @Override
    public Map<String, String> updateCartDetail(Long id, CartDetailRequestDto cartDetailRequestDto) {
        return Map.of();
    }
}
