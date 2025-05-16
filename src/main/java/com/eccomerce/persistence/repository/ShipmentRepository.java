package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
