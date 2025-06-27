package com.eccomerce.persistence.repository;

import com.eccomerce.persistence.entity.Client;
import com.eccomerce.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c JOIN c.userEntity u WHERE u.username = :username")
    Optional<Client> findByUsername(@Param("username") String username);

}
