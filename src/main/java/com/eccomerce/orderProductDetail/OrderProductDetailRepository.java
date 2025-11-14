package com.eccomerce.orderProductDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetail, Long> {

    @Query("SELECT opd.id FROM OrderProductDetail opd JOIN opd.order o JOIN o.client c WHERE c.id = :idClient AND opd.id = :idOrderProductDetail ")
    Optional<Long> checkClientOrderProductDetail(@Param("idClient") Long idClient, @Param("idOrderProductDetail") Long idOrderProductDetail);

    @Query("""
    SELECT opd
    FROM OrderProductDetail opd
    JOIN opd.order o
    WHERE (:clientId IS NULL OR o.client.id = :clientId)
      AND (:orderId IS NULL OR o.id = :orderId)
""")
    List<OrderProductDetail> findByClientAndOrder(
            @Param("clientId") Long clientId,
            @Param("orderId") Long orderId
    );

    @Query("""
SELECT opd
FROM OrderProductDetail opd
WHERE opd.order.id = :orderId
""")
    List<OrderProductDetail> findByOrderId(@Param("orderId") Long orderId);




}
