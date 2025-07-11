package com.eccomerce.service;

import com.eccomerce.exception.CartDetailNotFoundException;
import com.eccomerce.exception.ClientNotFoundException;
import com.eccomerce.exception.InvalidStockException;
import com.eccomerce.persistence.dto.response.CartDetailRequestDto;
import com.eccomerce.persistence.dto.response.CartDetailResponseDto;
import com.eccomerce.persistence.dto.response.ProductResponseDto;
import com.eccomerce.persistence.entity.*;
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

import java.util.*;

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

        if (product.getStock() == 0 || product.getStock() < cartDetailRequestDto.getQuantity()) {
            log.error("No hay suficiente stock para agregar al carrito");
            throw new InvalidStockException("No hay suficiente stock para agregar al carrito");
        }
        // Obtiene el nombre de usuario del cliente actualmente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("HOLAAAAA" +username);
        //String username = "lucas";

        // Busca el cliente asociado al USERNAME del usuario logueado
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new NoSuchElementException("Error no existe un cliente con ese ID"));

        // Verifica si el cliente tiene un carrito creado, caso contrario creara uno
        Optional<Cart> optionalCart = cartRepository.findByClientId(client.getId());
        Cart cart;

        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = Cart.builder()
                    .client(client)
                    .build();
            cartRepository.save(cart);
        }

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

        String username = getCurrentUsername();

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("ERROR el cliente " + username + " no existe" ));

        // Verifica si el cliente tiene un carrito creado, caso contrario creara uno
        Cart cart = cartRepository.findByClientId(client.getId()).orElse(Cart.builder()
                .client(client)
                .build());
        cartRepository.save(cart);

        List<CartDetail> cartDetailList = cartDetailRepository.findAll();

        return  cartDetailList.stream()
                .map(cartDetail -> {
                    return CartDetailResponseDto.builder()
                            .productResponseDto(ProductResponseDto.builder()
                                    .name(cartDetail.getProduct().getName())
                                    .imageUrl(cartDetail.getProduct().getImageUrl())
                                    .categoryDesc(cartDetail.getProduct().getCategory().getName())
                                    .price(cartDetail.getProduct().getPrice())
                                    .color(cartDetail.getProduct().getColor())
                                    .material(cartDetail.getProduct().getMaterial())
                                    .waist(cartDetail.getProduct().getWaist())
                                    .build())
                            .quantity(cartDetail.getQuantity())
                            .unitPrice(cartDetail.getUnitPrice())
                            .build();
                })
                .toList();
    }

    @Override
    public CartDetailResponseDto getCartDetailID(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteCartDetail(Long id) {

        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(()-> new CartDetailNotFoundException("El ID " + id + " no existe"));
        cartDetailRepository.delete(cartDetail);

        return ResponseEntity.ok("Hola");
    }

    @Override
    public ResponseEntity<?> updateCartDetail(Long id, Long quantity) {

        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(()-> new CartDetailNotFoundException("El ID " + id + " no existe"));

        if (quantity <= 0){
            throw new InvalidStockException("La cantidad de stock ingresado debe ser mayor a cero");
        }

        if (!cartDetail.getQuantity().equals(quantity)){

            cartDetail.setQuantity(quantity);
            cartDetailRepository.save(cartDetail);
        }


        return ResponseEntity.ok("Hola");
    }


    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
