package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Product as p SET p.stock = p.stock - :stock WHERE id = :id")
    void discountProductStock( @Param("stock") Long stock, @Param("id") Long id);

}
