package com.example.carservice.Services;

import com.example.carservice.Client;
import com.example.carservice.Repositories.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Force makes that every final property will be set null / 0 / false
 */
@NoArgsConstructor(force = true)
@ApplicationScoped
public class ClientService {

    private final ClientRepository clientRepository;

    @Inject
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

    public void updatePortrait(UUID id, InputStream is) {
        clientRepository.find(id).ifPresent(client ->{
            try{
                client.setPortrait(is.readAllBytes());
                clientRepository.update(client);
            } catch(IOException ex){
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deletePortrait(UUID id) {
        clientRepository.find(id).ifPresent(client ->{
            client.setPortrait(null);
            clientRepository.update(client);
        });
    }
}
