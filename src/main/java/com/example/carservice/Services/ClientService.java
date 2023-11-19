package com.example.carservice.Services;

import com.example.carservice.Client;
import com.example.carservice.Components.FileUtility;
import com.example.carservice.Repositories.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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

    private final FileUtility fileUtility = new FileUtility();

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

    @Transactional
    public void create(Client client){
        if(client.getId() == null)
            client.setId(UUID.randomUUID());
        clientRepository.create(client);
    }

    @Transactional
    public void update(Client client){
        clientRepository.update(client);
    }

    @Transactional
    public void delete(UUID id){
        clientRepository.delete(clientRepository.find(id).orElseThrow());
    }

    @Transactional
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

    @Transactional
    public void deletePortrait(UUID id) {
        clientRepository.find(id).ifPresent(client ->{
            fileUtility.deletePortrait(id);
        });
    }

    public byte[] getPortrait(UUID id) {
        return clientRepository.find(id)
                .map(Client::getPortrait)
                .orElseThrow(NotFoundException::new);
    }
}
