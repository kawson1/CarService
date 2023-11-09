package com.example.carservice.View.Client;

import com.example.carservice.Client;
import com.example.carservice.Services.ClientService;
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
public class ClientView implements Serializable {

    private final ClientService service;
    private final VisitService visitService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private Client client;

    @Inject
    public ClientView(ClientService service, VisitService visitService) {
        this.service = service;
        this.visitService = visitService;
    }


    public void init() throws IOException{
        service.find(id)
                .ifPresentOrElse(
                        client -> this.client = client,
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

//    public List<Visit> getVisits(){
//        return visitService.findAll().stream()
//                .filter(visit -> visit.getClient().getId().equals(client.getId()))
//                .toList();
//    }

    public String deleteAction(){
        service.delete(client.getId());
        visitService.findAll().stream()
                .filter(visit -> visit.client.getId().equals(id))
                .forEach(visit -> visitService.delete(visit.getId()));
        return "client_list?faces-redirect=true";
    }

//    public String deleteVisitAction(Visit visit){
//        visitService.find(visit.getId())
//                .ifPresent(v -> visitService.delete(visit.getId()));
//        return "client_view?faces-redirect=true";
//    }

}