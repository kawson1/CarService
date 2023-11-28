package com.example.carservice.Controllers;

import com.example.carservice.Client;
import com.example.carservice.ClientRoles;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Services.ClientService;

import com.example.carservice.dto.ClientResponse;
import jakarta.annotation.Resource;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DeclareRoles({
        ClientRoles.ADMIN,
        ClientRoles.USER
})
@Path("")
@Log
public class ClientControllerImplementation implements ClientController {

    private ClientService clientService;

    /**
     * Allows to create {@link jakarta.ws.rs.core.UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    @Inject
    public ClientControllerImplementation(
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(ClientService service) { this.clientService = service; }

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/clients/current-role")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> roles(){
        return Arrays.stream(ClientControllerImplementation.class.getAnnotation(DeclareRoles.class).value())
                .filter(roleName -> securityContext.isUserInRole(roleName))
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(ClientRoles.ADMIN)
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
    @RolesAllowed(ClientRoles.ADMIN)
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
    @RolesAllowed(ClientRoles.ADMIN)
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

    @RolesAllowed(ClientRoles.ADMIN)
    @Override
    public void update(Client client){
        try {
            clientService.update(client);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @RolesAllowed(ClientRoles.ADMIN)
    @Override
    public void delete(UUID id){
        clientService.find(id).ifPresentOrElse(
                entity -> clientService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @RolesAllowed(ClientRoles.ADMIN)
    @Override
    public byte[] getPortrait(UUID id) {
        if(clientService.find(id).isPresent())
            return clientService.getPortrait(id);
        else
            return new byte[0];
    }

    @RolesAllowed(ClientRoles.ADMIN)
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

    @RolesAllowed(ClientRoles.ADMIN)
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
