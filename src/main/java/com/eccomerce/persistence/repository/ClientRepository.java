package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
