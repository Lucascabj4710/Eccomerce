package com.eccomerce.cartDetail.service;

import com.eccomerce.cart.Cart;
import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.cartDetail.mapper.CartDetailResponseDtoMapper;
import com.eccomerce.cartDetail.mapper.CartDetailMapper;
import com.eccomerce.cartDetail.repository.CartDetailRepository;
import com.eccomerce.client.entity.Client;
import com.eccomerce.cartDetail.exception.CartDetailNotFoundException;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.product.exception.InvalidStockException;
import com.eccomerce.cartDetail.dto.CartDetailRequestDto;
import com.eccomerce.cartDetail.dto.CartDetailResponseDto;
import com.eccomerce.product.entity.Product;
import com.eccomerce.cart.CartRepository;
import com.eccomerce.client.repository.ClientRepository;
import com.eccomerce.product.exception.ProductNotFoundException;
import com.eccomerce.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class CartDetailServiceImpl implements CartDetailService{

    private final CartDetailMapper cartDetailMapper;
    private final CartDetailResponseDtoMapper cartDetailResponseDtoMapper;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public CartDetailServiceImpl(CartDetailMapper cartDetailMapper, CartDetailResponseDtoMapper cartDetailResponseDtoMapper, CartDetailRepository cartDetailRepository, CartRepository cartRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.cartDetailMapper = cartDetailMapper;
        this.cartDetailResponseDtoMapper = cartDetailResponseDtoMapper;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void createCartDetail(CartDetailRequestDto cartDetailRequestDto) {

        Product product = productRepository.findById(cartDetailRequestDto.getProduct()).orElseThrow(()-> new ProductNotFoundException("El producto no existe"));

        if (product.getStock() == 0 || product.getStock() < cartDetailRequestDto.getQuantity()) {
            log.error("No hay suficiente stock para agregar al carrito");
            throw new InvalidStockException("No hay suficiente stock para agregar al carrito");
        }

         String username = getCurrentUsername();
         log.info("El usuario logueado es: " + username);

        // Busca el cliente asociado al USERNAME del usuario logueado
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("Error no existe un cliente con ese ID"));

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

        Optional<CartDetail> CartDetailExists = cartDetailRepository.verificarCarritoDetalle(cart.getId(), product.getId());

        if(cartDetailRepository.verificarCarritoDetalle(cart.getId(), product.getId()).isPresent()){

            CartDetail cartDetail1 = CartDetailExists.get();

            if (cartDetailRequestDto.getQuantity() + cartDetail1.getQuantity()  <= product.getStock()) {
                cartDetailRepository.subirCantidad(cartDetailRequestDto.getQuantity(), cart.getId(), product.getId());
            } else {
                throw new InvalidStockException("Stock insuficiente para agregar al carrito");
            }

        } else {

            CartDetail cartDetail = CartDetail.builder()
                    .cart(cart)
                    .unitPrice(product.getPrice())
                    .quantity(cartDetailRequestDto.getQuantity())
                    .product(product)
                    .build();

            cartDetailRepository.save(cartDetail);
        }
    }

    @Override
    public List<CartDetailResponseDto> getCartDetail() {

        String username = getCurrentUsername();

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("ERROR el cliente " + username + " no existe" ));

        log.info("Hola este es el id: " + client.getUserEntity().getId());

        // Verifica si el cliente tiene un carrito creado, caso contrario creara uno
        Cart cart = cartRepository.findByClientId(client.getId()).orElseThrow(()-> new NoSuchElementException("No existe un carrito asociado a este cliente"));

        List<CartDetail> cartDetailList = cartDetailRepository.findByCartDetailID(cart.getId()).orElseThrow(()-> new CartDetailNotFoundException("Tu carrito está vacío o no tiene productos cargados."));

        return cartDetailList.stream()
                .map(cartDetailResponseDtoMapper::convertToCartDetailResponseDto)
                .toList();
    }

    @Override
    public CartDetailResponseDto getCartDetailID(Long id) {

        return cartDetailRepository.findById(id).map(cartDetailResponseDtoMapper::convertToCartDetailResponseDto)
                .orElseThrow(()-> new CartDetailNotFoundException("No se encontró un detalle de carrito con el ID: " + id));
    }

    @Override
    @Transactional
    public void deleteCartDetail(Long idCart, String productName) {
        Long idCartDetail = cartDetailRepository.IdCarritoDetalle(idCart, productName).orElseThrow(()-> new CartDetailNotFoundException("No existe ningún detalle en el carrito con el nombre de producto: " + productName));


        CartDetail cartDetail = cartDetailRepository.findById(idCartDetail).orElseThrow(()-> new CartDetailNotFoundException("El ID " + idCartDetail + " no existe"));
        cartDetailRepository.delete(cartDetail);
    }

    @Override
    @Transactional
    public void updateCartDetail(Long id, Long quantity) {

        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(()-> new CartDetailNotFoundException("El ID " + id + " no existe"));
        Product product = cartDetail.getProduct();
        log.info("Este es el producto cargado en el CartDetail: " + product);

        if (quantity <= 0){
            throw new InvalidStockException("La cantidad de stock ingresada debe ser mayor a cero");
        } else if (quantity > product.getStock()) {
            throw new InvalidStockException("La cantidad de stock ingresada es mayor al stock disponible");
        }

        if (!cartDetail.getQuantity().equals(quantity)){

            log.info("Actualizando cantidad de {} de {} a {}", product.getName(), cartDetail.getQuantity(), quantity);
            cartDetail.setQuantity(quantity);
            cartDetailRepository.save(cartDetail);
        }
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

