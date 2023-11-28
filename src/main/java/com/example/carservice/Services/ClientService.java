package com.example.carservice.Services;

import com.example.carservice.Client;
import com.example.carservice.Components.FileUtility;
import com.example.carservice.Repositories.ClientRepository;
import com.example.carservice.dto.ClientResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
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
@LocalBean
@Stateless
public class ClientService {

    private final ClientRepository clientRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Context
    private SessionContext sessionContext;

    @Inject
    public ClientService(ClientRepository clientRepository, Pbkdf2PasswordHash passwordHash){
        this.clientRepository = clientRepository;
        this.passwordHash = passwordHash;
    }

    public Optional<Client> find(UUID id){
        return clientRepository.find(id);
    }

    public Optional<Client> find(String name){
        return clientRepository.findByName(name);
    }

    public Optional<Client> findByLogin(String login){
        return clientRepository.findByLogin(login);
    }

    public List<Client> findAll() { return clientRepository.findAll(); }

    public void create(Client client){
        if(client.getId() == null)
            client.setId(UUID.randomUUID());
        client.setPassword(passwordHash.generate(client.getPassword().toCharArray()));
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

    public byte[] getPortrait(UUID id) {
        return clientRepository.find(id)
                .map(Client::getPortrait)
                .orElse(new byte[0]);
    }

    /**
     * @param login    client's login
     * @param password client's password
     * @return true if provided login and password are correct
     */
    public boolean verify(String login, String password) {
        return findByLogin(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    @PostConstruct
    void beanCreated(){
        System.out.println("Client service bean created!");
    }
}
