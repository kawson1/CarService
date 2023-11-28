package com.example.carservice.Repositories;

import com.example.carservice.Garage;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class GarageInMemoryRepository implements GarageRepository{

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Garage> findByTitle(String title) {
        return Optional.ofNullable(em.find(Garage.class, title));
    }

    @Override
    public Optional<Garage> find(UUID id) {
        return Optional.ofNullable(em.find(Garage.class, id));
    }

    @Override
    public List<Garage> findAll() {
        return em.createQuery("select g from Garage g", Garage.class).getResultList();
    }

    @Override
    public void create(Garage entity) { em.persist(entity); }

    @Override
    public void delete(Garage entity) { em.remove(em.find(Garage.class, entity.getId())); }

    @Override
    public void update(Garage entity) {
        em.merge(entity);
    }

}
