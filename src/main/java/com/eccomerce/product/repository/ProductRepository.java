package com.eccomerce.product.repository;

import com.eccomerce.product.entity.IsEnabledEnum;
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

    @Query("SELECT p FROM Product p WHERE p.name = :productName")
    Optional<Product> findByName(@Param("productName") String productName);

    // Materiales distintos de productos ENABLED
    @Query("SELECT DISTINCT p.material FROM Product p WHERE p.isEnabled = com.eccomerce.product.entity.IsEnabledEnum.ENABLED")
    List<String> findDistinctMaterials();

    // Buscar por material (solo ENABLED)
    @Query("SELECT p FROM Product p WHERE p.material LIKE %:material% AND p.isEnabled = com.eccomerce.product.entity.IsEnabledEnum.ENABLED")
    Page<Product> findByMaterialContainingIgnoreCase(String material, Pageable pageable);

    // Buscar por nombre (solo ENABLED)
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.isEnabled = com.eccomerce.product.entity.IsEnabledEnum.ENABLED")
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Buscar por nombre y material (solo ENABLED)
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.material LIKE %:material% AND p.isEnabled = com.eccomerce.product.entity.IsEnabledEnum.ENABLED")
    Page<Product> findByNameContainingIgnoreCaseAndMaterialContainingIgnoreCase(String name, String material, Pageable pageable);

    // Todos los productos ENABLED
    @Query("SELECT p FROM Product p WHERE p.isEnabled = com.eccomerce.product.entity.IsEnabledEnum.ENABLED")
    Page<Product> findAllEnabled(Pageable pageable);

}
