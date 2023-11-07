package com.example.carservice.View.Garage;

import com.example.carservice.Garage;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@RequestScoped
@Named
public class GarageList {

    private final GarageService service;
    private final VisitService visitService;

    @Inject
    public GarageList(GarageService service, VisitService visitService){
        this.service = service;
        this.visitService = visitService;
    }

    public List<Garage> getGarages(){
        return service.findAll();
    }

    public String deleteAction(Garage garage){
        service.delete(garage.getId());
        visitService.findAll().stream()
                .filter(visit -> visit.garage.getId().equals(garage.getId()))
                .forEach(visit -> visitService.delete(visit.getId()));
        return "garage_list?faces-redirect=true";
    }

}
