package com.eccomerce.userEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("SELECT U FROM UserEntity U WHERE U.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

}
