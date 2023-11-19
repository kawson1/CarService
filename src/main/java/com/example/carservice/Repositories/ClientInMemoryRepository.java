package com.example.carservice.Repositories;

import com.example.carservice.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ClientInMemoryRepository implements ClientRepository{

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Client> findByName(String name) {
        return Optional.ofNullable(em.find(Client.class, name));
    }

    @Override
    public Optional<Client> find(UUID id) {
        return Optional.ofNullable(em.find(Client.class, id));
    }

    @Override
    public List<Client> findAll() {
        return em.createQuery("select c from Client c", Client.class).getResultList();
    }

    @Override
    public void create(Client entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Client entity) {
        em.remove(em.find(Client.class, entity.getId()));
    }

    @Override
    public void update(Client entity) {
        em.merge(entity);
    }

}
