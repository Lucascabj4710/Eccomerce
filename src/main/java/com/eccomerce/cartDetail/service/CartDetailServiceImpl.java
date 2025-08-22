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
    public Map<String, String> createCartDetail(CartDetailRequestDto cartDetailRequestDto) {

        Product product = productRepository.findById(cartDetailRequestDto.getProduct()).orElseThrow(()-> new NoSuchElementException("El producto no existe"));

        if (product.getStock() == 0 || product.getStock() < cartDetailRequestDto.getQuantity()) {
            log.error("No hay suficiente stock para agregar al carrito");
            throw new InvalidStockException("No hay suficiente stock para agregar al carrito");
        }
        // Obtiene el nombre de usuario del cliente actualmente logueado
      //  String username = SecurityContextHolder.getContext().getAuthentication().getName();
       // log.debug(username);
         String username = "lucas";

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

//            // Codigo temporal para testear descuento de STOCK
//            if (product.getStock() == 0 || product.getStock() < cartDetail.getQuantity()) {
//                log.error("No se puede actualizar el stock");
//                throw new RuntimeException("ERROR");
//            } else {
//                productRepository.discountProductStock(cartDetail.getQuantity(), product.getId());
//            }
        }


        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public List<CartDetailResponseDto> getCartDetail() {

        // String username = getCurrentUsername();
        String username = "lucas";

        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new ClientNotFoundException("ERROR el cliente " + username + " no existe" ));

        log.info("Hola este es el id: " + client.getUserEntity().getId());

        // Verifica si el cliente tiene un carrito creado, caso contrario creara uno
        Cart cart = cartRepository.findByClientId(client.getId()).orElseThrow(()-> new NoSuchElementException("OPA"));

        List<CartDetail> cartDetailList = cartDetailRepository.findByCartDetailID(cart.getId()).orElseThrow(()-> new CartDetailNotFoundException("OPA"));

        return cartDetailList.stream()
                .map(cartDetailResponseDtoMapper::convertToCartDetailResponseDto)
                .toList();
    }

    @Override
    public CartDetailResponseDto getCartDetailID(Long id) {

        return cartDetailRepository.findById(id).map(cartDetailResponseDtoMapper::convertToCartDetailResponseDto)
                .orElseThrow(()-> new CartDetailNotFoundException("Error no hay un cartDetail con id: " + id));
    }

    @Override
    public Map<String, String> deleteCartDetail(Long idCart, String productName) {


        Long idCartDetail = cartDetailRepository.IdCarritoDetalle(idCart, productName).orElseThrow(()-> new CartDetailNotFoundException("Error no existe el cartDetail con ese id"));

        CartDetail cartDetail = cartDetailRepository.findById(idCartDetail).orElseThrow(()-> new CartDetailNotFoundException("El ID " + idCartDetail + " no existe"));
        cartDetailRepository.delete(cartDetail);

        return Map.of("STATUS", "COMPLETED");
    }

    @Override
    public Map<String, String> updateCartDetail(Long id, Long quantity) {

        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(()-> new CartDetailNotFoundException("El ID " + id + " no existe"));

        if (quantity <= 0){
            throw new InvalidStockException("La cantidad de stock ingresado debe ser mayor a cero");
        }

        if (!cartDetail.getQuantity().equals(quantity)){

            cartDetail.setQuantity(quantity);
            cartDetailRepository.save(cartDetail);
        }
        return Map.of("STATUS", "COMPLETED");
    }


//    public CartDetailResponseDto convertToCartDetailResponseDto(CartDetail cartDetail){
//        if(modelMapper.getTypeMap(CartDetail.class, CartDetailResponseDto.class) == null){
//            TypeMap<CartDetail, CartDetailResponseDto> propertyMapper = modelMapper.createTypeMap(CartDetail.class, CartDetailResponseDto.class);
//            propertyMapper.addMappings(mapping ->
//                    mapping.skip(CartDetailResponseDto::setProductResponseDto));
//            propertyMapper.addMapping(CartDetail::getUnitPrice, CartDetailResponseDto::setUnitPrice);
//            propertyMapper.addMapping(CartDetail::getQuantity, CartDetailResponseDto::setQuantity);
//        }
//        return modelMapper.map(cartDetail, CartDetailResponseDto.class);
//    }
//
//    public CartDetail convertToCartDetail(CartDetailResponseDto cartDetailResponseDto){
//        if(modelMapper.getTypeMap(CartDetailResponseDto.class, CartDetail.class) == null) {
//            TypeMap<CartDetailResponseDto, CartDetail> propertyMapper = modelMapper.createTypeMap(CartDetailResponseDto.class, CartDetail.class);
//            propertyMapper.addMappings(mapping -> mapping.skip(CartDetail::setProduct));
//            propertyMapper.addMappings(mapping -> mapping.skip(CartDetail::setCart));
//            propertyMapper.addMapping(CartDetailResponseDto::getQuantity, CartDetail::setQuantity);
//            propertyMapper.addMapping(CartDetailResponseDto::getUnitPrice, CartDetail::setUnitPrice);
//        }
//        return modelMapper.map(cartDetailResponseDto, CartDetail.class);
//    }



    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

