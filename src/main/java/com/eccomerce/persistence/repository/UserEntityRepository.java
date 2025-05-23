package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
