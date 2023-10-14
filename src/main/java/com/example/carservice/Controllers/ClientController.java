package com.example.carservice.Controllers;

import com.example.carservice.Client;
import com.example.carservice.Services.ClientService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    public Client find(UUID id){
        return clientService.find(id)
                .orElseThrow();
    }

    public Client find(String name){
        return clientService.find(name)
                .orElseThrow();
    }

    public List<Client> getClients(){
        return clientService.findAll();
    }

    public void create(Client client){
        clientService.create(client);
    }

    public void update(Client client){
        clientService.update(client);
    }

    public void delete(UUID id){
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.delete(id),
                () -> {
                }
        );
    }

    public void putClientPortrait(UUID uuid, InputStream portrait) {
    }
}
