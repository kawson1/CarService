package com.example.carservice.Repositories;

import com.example.carservice.Visit;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class VisitInMemoryRepository implements VisitRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Visit> find(UUID id) {
        return Optional.ofNullable(em.find(Visit.class, id));
    }

    @Override
    public Optional<Visit> findByVIN(String VIN) {
        return Optional.ofNullable(em.find(Visit.class, VIN));
    }

    @Override
    public List<Visit> findAll() {
        return em.createQuery("select v from Visit v", Visit.class).getResultList();
    }

    @Override
    public List<Visit> findAllByDate(Date date) {
        return em.createQuery("select v from Visit v where v.date = :date", Visit.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Visit> findAllByGarageId(UUID garageId) {
        return em.createQuery("select v from Visit v where v.garage.id = :garageId", Visit.class)
                .setParameter("garageId", garageId)
                .getResultList();
    }

    @Override
    public void create(Visit entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Visit entity) {
        em.remove(em.find(Visit.class, entity.getId()));
    }

    @Override
    public void update(Visit entity) {
        em.merge(entity);
    }

}
