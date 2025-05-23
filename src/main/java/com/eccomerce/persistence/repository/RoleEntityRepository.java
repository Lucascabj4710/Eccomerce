package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
}
