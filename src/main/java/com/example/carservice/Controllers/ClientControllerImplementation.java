package com.example.carservice.Controllers;

import com.example.carservice.Client;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Services.ClientService;

import com.example.carservice.dto.ClientResponse;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Path("")
@Log
public class ClientControllerImplementation implements ClientController {

    private final ClientService clientService;

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    @Inject
    public ClientControllerImplementation(
            ClientService clientService,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.clientService = clientService;
        this.uriInfo = uriInfo;
    }

    @Override
    public ClientResponse find(UUID id){
        return clientService.find(id)
                .map(client -> ClientResponse.builder()
                        .surname(client.getSurname())
                        .id(client.getId())
                        .clientType(client.getClientType())
                        .name(client.getName())
                        .build())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ClientResponse find(String name){
        return clientService.find(name)
                .map(client -> ClientResponse.builder()
                        .surname(client.getSurname())
                        .id(client.getId())
                        .clientType(client.getClientType())
                        .name(client.getName())
                        .build())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<ClientResponse> getClients(){
        return clientService.findAll()
                .stream()
                .map(client -> ClientResponse.builder()
                        .surname(client.getSurname())
                        .id(client.getId())
                        .clientType(client.getClientType())
                        .name(client.getName())
                        .build())
                .toList();
    }

    @Override
    public void create(Client client){
        try {
            clientService.create(client);
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ClientController.class, "find")
                    .build(client.getId())
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void update(Client client){
        try {
            clientService.update(client);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void delete(UUID id){
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getPortrait(UUID id) {
        if(clientService.find(id).isPresent())
            return clientService.getPortrait(id);
        else
            throw new NotFoundException();
    }

    @Override
    public void putClientPortrait(UUID id, InputStream portrait) {
        clientService.find(id).ifPresentOrElse(
                entity -> {
                    clientService.updatePortrait(id, portrait);
                    response.setHeader("Location", uriInfo.getBaseUriBuilder()
                            .path(ClientController.class, "getPortrait")
                            .build(id)
                            .toString());
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteClientPortrait(UUID id) {
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
