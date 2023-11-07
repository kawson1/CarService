package com.example.carservice.View.Garage;

import com.example.carservice.Client;
import com.example.carservice.Garage;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.GarageService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class GarageEdit implements Serializable {

    private final GarageService service;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private Garage garage;

    @Inject
    public GarageEdit(GarageService service) {
        this.service = service;
    }


    public void init() throws IOException {
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

    public String saveAction() throws IOException {
        service.update(garage);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
