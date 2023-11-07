package com.example.carservice.View.Visit;

import com.example.carservice.Client;
import com.example.carservice.Garage;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
public class VisitList {

    private final VisitService service;

    @Setter
    @Getter
    private UUID categoryId;

    @Inject
    public VisitList(VisitService service){
        this.service = service;
    }

    /**
     * we should create interface for client, garage, to use generic type instead of creating 2 methods
     * which are the same
     */
    public List<Visit> getVisits(Client client){
        return service.findAll().stream()
                .filter(visit -> visit.getClient().getId().equals(client.getId()))
                .toList();
    }
    public List<Visit> getVisits(Garage garage){
        return service.findAll().stream()
                .filter(visit -> visit.getGarage().getId().equals(garage.getId()))
                .toList();
    }

    public List<Visit> getAllVisits(){
        return service.findAll();
    }

    public void deleteAction(Visit visit){
        service.delete(visit.getId());
        //return "visit_list?faces-redirect=true";
    }

}
