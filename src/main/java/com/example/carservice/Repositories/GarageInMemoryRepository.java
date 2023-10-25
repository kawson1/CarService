package com.example.carservice.Repositories;

import com.example.carservice.Components.DataStore;
import com.example.carservice.Garage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class GarageInMemoryRepository implements GarageRepository{

    private final DataStore store;

    @Inject
    public GarageInMemoryRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Garage> findByTitle(String title) {
        return store.findAllGarages().stream()
                .filter(garage -> garage.title.equals(title))
                .findFirst();
    }

    @Override
    public Optional<Garage> find(UUID id) {
        return store.findAllGarages().stream()
                .filter(garage -> garage.id.equals(id))
                .findFirst();
    }

    @Override
    public List<Garage> findAll() {
        return store.findAllGarages();
    }

    @Override
    public void create(Garage entity) { store.createGarage(entity); }

    @Override
    public void delete(Garage entity) { store.deleteGarage(entity); }

    @Override
    public void update(Garage entity) {
        store.updateGarage(entity);
    }

}
