package com.example.carservice.Repositories;

import com.example.carservice.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends Repository<Client, UUID>{

    Optional<Client> findByName(String name);

}
