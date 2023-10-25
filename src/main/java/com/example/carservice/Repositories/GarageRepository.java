package com.example.carservice.Repositories;

import com.example.carservice.Garage;

import java.util.Optional;
import java.util.UUID;

public interface GarageRepository extends Repository<Garage, UUID> {

    Optional<Garage> findByTitle(String title);

}
