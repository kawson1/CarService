package com.example.carservice.Repositories;

import com.example.carservice.Components.DataStore;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class VisitInMemoryRepository implements VisitRepository {

    private final DataStore store;

    @Inject
    public VisitInMemoryRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Visit> find(UUID id) {
        return store.findAllVisits().stream()
                .filter(visit -> visit.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Visit> findByVIN(String VIN) {
        return store.findAllVisits().stream()
                .filter(visit -> visit.getVIN().equals(VIN))
                .findFirst();
    }

    @Override
    public List<Visit> findAll() {
        return store.findAllVisits();
    }

    @Override
    public List<Visit> findAllByDate(Date date) {
        return store.findAllVisits().stream()
                .filter(visit -> visit.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Visit entity) {
        store.createVisit(entity);
    }

    @Override
    public void delete(Visit entity) {
        store.deleteVisit(entity);
    }

    @Override
    public void update(Visit entity) {
        store.updateVisit(entity);
    }

}
