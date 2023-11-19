package com.example.carservice.Services;

import com.example.carservice.Repositories.VisitRepository;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(force = true)
@ApplicationScoped
public class VisitService {

    private final VisitRepository visitRepository;

    private final GarageService garageService;

    @Inject
    public VisitService(VisitRepository visitRepository, GarageService garageService){
        this.visitRepository = visitRepository;
        this.garageService = garageService;
    }

    public Optional<Visit> find(UUID id){
        return visitRepository.find(id);
    }

    public Optional<Visit> find(String vin){
        return visitRepository.findByVIN(vin);
    }

    public List<Visit> findAll() { return visitRepository.findAll(); }

    public List<Visit> findAll(UUID garageId) { return visitRepository.findAllByGarageId(garageId); }

    @Transactional
    public void create(Visit visit, UUID garageId){
        if(visit.getId() == null)
            visit.setId(UUID.randomUUID());
        garageService.find(garageId).ifPresentOrElse(
                garage -> {
                    visit.setGarage(garage);
                    visitRepository.create(visit);
                },
                () -> {
                    throw new IllegalArgumentException("Garage does not exists.");
                });
    }

    @Transactional
    public void create(Visit visit){
        if(visit.getId() == null)
            visit.setId(UUID.randomUUID());
        visitRepository.create(visit);
    }

    @Transactional
    public void update(Visit visit){
        visitRepository.update(visit);
    }

    @Transactional
    public void delete(UUID id){
/*
        garageRepository.delete(garageRepository.find(id).orElseThrow());
*/
        visitRepository.find(id).ifPresentOrElse(
                entity -> visitRepository.delete(entity),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
