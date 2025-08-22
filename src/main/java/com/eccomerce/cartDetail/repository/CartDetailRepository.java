package com.eccomerce.cartDetail.repository;

import com.eccomerce.cartDetail.entity.CartDetail;
import com.eccomerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    @Query("SELECT c.product.id FROM CartDetail c WHERE c.cart.id = :idCart AND c.product.id = :idProduct")
    Optional<Long> verificarCarritoDetalle(@Param("idCart") Long idCart, @Param("idProduct") Long idProduct);

    @Query("SELECT c.id FROM CartDetail c WHERE c.cart.id = :idCart AND c.product.name = :name")
    Optional<Long> IdCarritoDetalle(@Param("idCart") Long idCart, @Param("name") String name);


    @Query("SELECT c.product.id FROM CartDetail c WHERE c.cart.id = :idCart AND c.product.id = :idProduct")
    Optional<Product> verificarCarritoDetalleProducto(@Param("idCart") Long idCart, @Param("idProduct") Long idProduct);

    @Transactional
    @Modifying
    @Query("UPDATE CartDetail as c SET c.quantity = c.quantity + :cantidad WHERE c.cart.id = :idCart AND c.product.id = :idProduct ")
    void subirCantidad(@Param("cantidad") Long cantidad, @Param("idCart") Long idCart, @Param("idProduct") Long idProduct);

    @Query("SELECT c from CartDetail c WHERE c.cart.id = :idCart")
    Optional<List<CartDetail>> findByCartDetailID(@Param("idCart") Long idCart);


}
