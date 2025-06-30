package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    @Query("SELECT c.product.id FROM CartDetail c WHERE c.cart.id = :idCart AND c.product.id = :idProduct")
    Optional<Long> verificarCarritoDetalle(@Param("idCart") Long idCart, @Param("idProduct") Long idProduct);

    @Transactional
    @Modifying
    @Query("UPDATE CartDetail as c SET c.quantity = c.quantity + :cantidad WHERE c.cart.id = :idCart AND c.product.id = :idProduct ")
    void subirCantidad(@Param("cantidad") Long cantidad, @Param("idCart") Long idCart, @Param("idProduct") Long idProduct);


}
