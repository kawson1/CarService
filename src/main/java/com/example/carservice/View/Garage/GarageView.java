package com.example.carservice.View.Garage;

import com.example.carservice.Garage;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * View bean for rendering single character edit form.
 */
@ViewScoped
@Named
public class GarageView implements Serializable{

    private final GarageService service;
    private final VisitService visitService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private Garage garage;

    @Inject
    public GarageView(GarageService service, VisitService visitService) {
        this.service = service;
        this.visitService = visitService;
    }


    public void init() throws IOException{
        service.find(id)
                .ifPresentOrElse(
                        garage -> this.garage = garage,
                        () -> {
                            try {
                                FacesContext
                                        .getCurrentInstance()
                                        .getExternalContext()
                                        .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
    }

    public String deleteAction(){
        service.delete(garage.getId());
        visitService.findAll().stream()
                .filter(visit -> visit.garage.getId().equals(garage.getId()))
                .forEach(visit -> visitService.delete(visit.getId()));
        return "garage_list?faces-redirect=true";
    }
}