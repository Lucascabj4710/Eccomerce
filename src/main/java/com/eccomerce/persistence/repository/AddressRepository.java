package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
