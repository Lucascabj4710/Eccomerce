package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByClientId(Long idClient);

}
