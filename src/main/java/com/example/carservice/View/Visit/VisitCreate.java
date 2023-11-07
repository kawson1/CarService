package com.example.carservice.View.Visit;

import com.example.carservice.Client;
import com.example.carservice.Garage;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class VisitCreate implements Serializable {

    private final VisitService service;
    private final GarageService garageService;
    private final ClientService clientService;

    @Setter
    @Getter
    private Visit visit;

    @Setter
    @Getter
    private Garage garage;

    @Setter
    @Getter
    private Client client;

    @Getter
    private List<Garage> garages;

    @Getter
    private List<Client> clients;

    @Inject
    public VisitCreate(VisitService service, GarageService garageService, ClientService clientService){
        this.service = service;
        this.garageService = garageService;
        this.clientService = clientService;
    }

    public void init(){
        this.visit = Visit.builder()
                .id(UUID.randomUUID())
                .build();
        this.garages = garageService.findAll();
        this.clients = clientService.findAll();
    }

    public String saveAction(){
        visit.setGarage(garageService.find(garage.getId()).get());
        visit.setClient(clientService.find(client.getId()).get());
        service.create(this.visit);
        return "visit_list.xhtml?faces-redirect=true";
    }
}
