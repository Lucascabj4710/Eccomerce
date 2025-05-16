package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.OrderProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetail, Long> {
}
