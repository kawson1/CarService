package com.example.carservice.View.Client;

import com.example.carservice.Client;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.VisitService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@RequestScoped
@Named
public class ClientList {

    private final ClientService service;
    private final VisitService visitService;

    @Inject
    public ClientList(ClientService service, VisitService visitService){
        this.service = service;
        this.visitService = visitService;
    }

    public List<Client> getClients(){
        return service.findAll();
    }

    public String deleteAction(Client client){
        service.delete(client.getId());
        visitService.findAll().stream()
                .filter(visit -> visit.client.getId().equals(client.getId()))
                .forEach(visit -> visitService.delete(visit.getId()));
        return "client_list?faces-redirect=true";
    }

}
