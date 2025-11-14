package com.eccomerce.client.repository;

import com.eccomerce.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c JOIN c.userEntity u WHERE u.username = :username")
    Optional<Client> findByUsername(@Param("username") String username);

    Optional<Client> findByEmail(String email);
}
