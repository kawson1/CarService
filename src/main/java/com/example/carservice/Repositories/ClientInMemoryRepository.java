package com.example.carservice.Repositories;

import com.example.carservice.Client;
import com.example.carservice.Components.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientInMemoryRepository implements ClientRepository{

    private final DataStore store;

    public ClientInMemoryRepository(DataStore store){
        this.store = store;
    }

    @Override
    public Optional<Client> findByName(String name) {
        return store.findAllClients().stream()
                .filter(client -> client.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Client> find(UUID id) {
        return store.findAllClients().stream()
                .filter(client -> client.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Client> findAll() {
        return store.findAllClients();
    }

    @Override
    public void create(Client entity) {
        store.createClient(entity);
    }

    @Override
    public void delete(Client entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(Client entity) {
        store.updateClient(entity);
    }

}
