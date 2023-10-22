package com.example.carservice.Controllers;

import com.example.carservice.Client;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Controllers.Exception.NotFoundException;
import com.example.carservice.Services.ClientService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class ClientControllerImplementation implements ClientController {

    private final ClientService clientService;

    @Inject
    public ClientControllerImplementation(ClientService clientService){
        this.clientService = clientService;
    }

    public Client find(UUID id){
        return clientService.find(id)
                .orElseThrow(NotFoundException::new);
    }

    public Client find(String name){
        return clientService.find(name)
                .orElseThrow(NotFoundException::new);
    }

    public List<Client> getClients(){
        return clientService.findAll();
    }

    public void create(Client client){
        try {
            clientService.update(client);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    public void update(Client client){
        try {
            clientService.update(client);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    public void delete(UUID id){
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public byte[] getPortrait(UUID id) {
        return clientService.find(id)
                .map(Client::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    public void putClientPortrait(UUID id, InputStream portrait) {
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public void deleteClientPortrait(UUID id) {
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
