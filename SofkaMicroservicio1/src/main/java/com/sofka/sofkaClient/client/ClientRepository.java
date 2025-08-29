package com.sofka.sofkaClient.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c " +
            "from Client c " +
            "where c.id=?1 and c.deletedAt is null")
    Optional<Client> findClientById(Long id);

    @Query("select c " +
            "from Client c " +
            "where c.username=?1 and c.deletedAt is null")
    Optional<Client> findClientByUsername(String username);

}

