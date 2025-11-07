package com.eccomerce.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.client.id = :idClient")
    Optional<Order> findByClient(@Param("idClient") Long idClient);

    @Query("SELECT o FROM Order o WHERE o.client.id = :idClient AND o.id = :idOrder")
    Optional<Order> findByClientAndOrderID(@Param("idClient") Long idClient, @Param("idOrder") Long idOrder);



}
