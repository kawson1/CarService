package com.example.carservice.Services;

import com.example.carservice.Controllers.Exception.NotFoundException;
import com.example.carservice.Garage;
import com.example.carservice.Components.FileUtility;
import com.example.carservice.Repositories.GarageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Force makes that every final property will be set null / 0 / false
 */
@NoArgsConstructor(force = true)
@ApplicationScoped
public class GarageService {

    private final GarageRepository garageRepository;

    @Inject
    public GarageService(GarageRepository GarageRepository){
        this.garageRepository = GarageRepository;
    }

    public Optional<Garage> find(UUID id){
        return garageRepository.find(id);
    }

    public Optional<Garage> find(String title){
        return garageRepository.findByTitle(title);
    }

    public List<Garage> findAll() { return garageRepository.findAll(); }

    public void create(Garage Garage){
        garageRepository.create(Garage);
    }

    public void update(Garage Garage){
        garageRepository.update(Garage);
    }

    public void delete(UUID id){
/*
        garageRepository.delete(garageRepository.find(id).orElseThrow());
*/
        garageRepository.find(id).ifPresentOrElse(
                entity -> garageRepository.delete(entity),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
