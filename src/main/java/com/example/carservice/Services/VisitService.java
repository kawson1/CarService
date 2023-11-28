package com.example.carservice.Services;

import com.example.carservice.Repositories.VisitRepository;
import com.example.carservice.Visit;
import com.example.carservice.dto.CreateVisitRequest;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(force = true)
@LocalBean
@Stateless
public class VisitService {

    private final VisitRepository visitRepository;

    private final GarageService garageService;

    private final ClientService clientService;

    @Inject
    public VisitService(VisitRepository visitRepository, GarageService garageService, ClientService clientService){
        this.visitRepository = visitRepository;
        this.garageService = garageService;
        this.clientService = clientService;
    }

    public Optional<Visit> find(UUID id){
        return visitRepository.find(id);
    }

    public Optional<Visit> find(String vin){
        return visitRepository.findByVIN(vin);
    }

    public List<Visit> findAll() { return visitRepository.findAll(); }

    public List<Visit> findAll(UUID garageId) { return visitRepository.findAllByGarageId(garageId); }

    public List<Visit> findAllByClientId(UUID clientId) { return visitRepository.findAllByClientId(clientId); }

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

    public void create(CreateVisitRequest visit){
        Visit v = new Visit();
        if(visit.getId() == null)
            visit.setId(UUID.randomUUID());
        garageService.find(visit.getGarageId()).ifPresentOrElse(
                garage -> {
                    v.setGarage(garage);
                },
                () -> {
                    throw new IllegalArgumentException("Garage does not exists.");
                });
        clientService.find(visit.getClientId()).ifPresentOrElse(
                client -> {
                    v.setClient(client);
                },
                () -> {
                    throw new IllegalArgumentException("Client does not exists.");
                });
        v.setId(visit.getId());
        v.setDate(visit.getDate());
        v.setVIN(visit.getVIN());
        visitRepository.create(v);
    }

    public void create(Visit visit){
        if(visit.getId() == null)
            visit.setId(UUID.randomUUID());
        visitRepository.create(visit);
    }

    public void update(Visit visit){
        visitRepository.update(visit);
    }

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
