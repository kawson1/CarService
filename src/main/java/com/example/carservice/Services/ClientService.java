package com.example.carservice.Services;

import com.example.carservice.Client;
import com.example.carservice.Repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Optional<Client> find(UUID id){
        return clientRepository.find(id);
    }

    public Optional<Client> find(String name){
        return clientRepository.findByName(name);
    }

    public List<Client> findAll() { return clientRepository.findAll(); }

    public void create(Client client){
        clientRepository.create(client);
    }

    public void update(Client client){
        clientRepository.update(client);
    }

    public void delete(UUID id){
        clientRepository.delete(clientRepository.find(id).orElseThrow());
    }

}
