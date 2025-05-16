package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
