package com.eccomerce.cart;

import com.eccomerce.cart.exception.CartNotFoundException;
import com.eccomerce.client.entity.Client;
import com.eccomerce.client.exception.ClientNotFoundException;
import com.eccomerce.client.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ClientRepository clientRepository;

    public CartService(CartRepository cartRepository, ClientRepository clientRepository) {
        this.cartRepository = cartRepository;
        this.clientRepository = clientRepository;
    }

    public Long getCartId(){

        Client client = clientRepository.findByUsername(getCurrentUsername()).orElseThrow(()-> new ClientNotFoundException("Cliente no existe con ese username"));
        Cart cart = cartRepository.findByClientId(client.getId()).orElseThrow(()-> new CartNotFoundException("Carrito no encontrado"));

        return cart.getId();
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
