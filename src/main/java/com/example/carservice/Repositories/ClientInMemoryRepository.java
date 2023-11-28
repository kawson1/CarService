package com.example.carservice.Repositories;

import com.example.carservice.Client;
import com.example.carservice.Visit;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        try {
            return Optional.of(em.createQuery("select c from Client c where c.name = :name", Client.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> findByLogin(String login) {
        return Optional.ofNullable(em.createQuery("select c from Client c where c.login = :login", Client.class)
                .setParameter("login", login)
                .getResultList()
                .stream().findFirst()
                .orElse(null));
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
