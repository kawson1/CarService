package com.example.carservice.Repositories;

import com.example.carservice.Visit;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitRepository extends Repository<Visit, UUID> {

    Optional<Visit> findByVIN(String VIN);

    List<Visit> findAllByDate(Date date);

    List<Visit> findAllByGarageId(UUID garageId);

    List<Visit> findAllByClientId(UUID clientId);
}
