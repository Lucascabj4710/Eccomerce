package com.eccomerce.product.repository;

import com.eccomerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Product as p SET p.stock = p.stock - :stock WHERE id = :id")
    void discountProductStock( @Param("stock") Long stock, @Param("id") Long id);

    @Query("SELECT p FROM Product as p WHERE p.name = :name")
    Optional<Product> findByName(@Param("name") String productName);

    @Query("SELECT DISTINCT p.material FROM Product p")
    List<String> findDistinctMaterials();

    Page<Product> findByMaterialContainingIgnoreCase(String material, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndMaterialContainingIgnoreCase(String name, String material, Pageable pageable);

}
