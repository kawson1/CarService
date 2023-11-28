package com.example.carservice.View.Client;

import com.example.carservice.Client;
import com.example.carservice.Services.ClientService;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static java.lang.System.out;

@ViewScoped
@Named
public class ClientEdit implements Serializable {

    private ClientService service;

    @Setter
    @Getter
    private UUID id;

    /**
     * Multipart part for uploaded portrait file.
     */
    @Setter
    @Getter
    private Part portrait;

    @Getter
    private Client client;

    @EJB
    public void setClientService(ClientService service) { this.service = service; }

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

    public String saveAction() throws IOException {
        service.update(client);
        if(portrait != null) {
            service.updatePortrait(id, portrait.getInputStream());
        }
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}