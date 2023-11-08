package com.example.carservice.Services;

import com.example.carservice.Controllers.Exception.NotFoundException;
import com.example.carservice.Garage;
import com.example.carservice.Repositories.GarageRepository;
import com.example.carservice.Repositories.VisitRepository;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(force = true)
@ApplicationScoped
public class VisitService {

    private final VisitRepository visitRepository;

    @Inject
    public VisitService(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }

    public Optional<Visit> find(UUID id){
        return visitRepository.find(id);
    }

    public Optional<Visit> find(String vin){
        return visitRepository.findByVIN(vin);
    }

    public List<Visit> findAll() { return visitRepository.findAll(); }

    public List<Visit> findAll(UUID garageId) {
        return visitRepository.findAll()
            .stream().filter(visit -> visit.garage.getId().equals(garageId)).toList();
    }

    public void create(Visit visit){
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
