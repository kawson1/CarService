package com.example.carservice.Controllers;

import com.example.carservice.Client;
import com.example.carservice.ClientRoles;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Garage;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import com.example.carservice.dto.CreateVisitRequest;
import com.example.carservice.dto.VisitResponse;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("")
@DeclareRoles({ClientRoles.ADMIN, ClientRoles.USER})
public class VisitControllerImplementation implements VisitController {

    private VisitService visitService;

    private ClientService clientService;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    SecurityContext securityContext;

    @Context
    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    @Inject
    public VisitControllerImplementation(@SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) { this.uriInfo = uriInfo; }

    @EJB
    public void setService(VisitService service) { this.visitService = service; }

    @EJB
    public void setClientService(ClientService service) { this.clientService = service; }

    @Override
    @RolesAllowed(ClientRoles.ADMIN)
    public Visit find(UUID id, UUID garageId) {
        return visitService.findAll(garageId)
                .stream().filter(visit -> visit.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(ClientRoles.ADMIN)
    public List<VisitResponse> getVisits() {
        return visitService.findAll()
                .stream()
                .map(visit -> VisitResponse.builder()
                        .id(visit.getId())
                        .VIN(visit.getVIN())
                        .date(visit.getDate())
                        .build())
                .toList();
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN})
    public List<VisitResponse> getGarageVisits(UUID garageId) {
        return visitService.findAll(garageId)
                .stream()
                .map(visit -> VisitResponse.builder()
                        .id(visit.getId())
                        .VIN(visit.getVIN())
                        .date(visit.getDate())
                        .build())
                .toList();
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public List<VisitResponse> getClientVisits(UUID clientId) {
        if(!securityContext.isUserInRole(ClientRoles.ADMIN)){
            String login = clientService.find(clientId).map(client -> client.getLogin()).orElseThrow(NotFoundException::new);
            if(!securityContext.getUserPrincipal().getName().equals(login)) {
                throw new NotAuthorizedException("You Don't Have Permission");
            }
        }
        return visitService.findAllByClientId(clientId)
                .stream()
                .map(visit -> VisitResponse.builder()
                .id(visit.getId())
                .VIN(visit.getVIN())
                .date(visit.getDate())
                .build())
                .toList();
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN})
    public void create(Visit visit, UUID garageId) {
        try {
            visitService.create(visit, garageId);
//            response.setHeader("Location", uriInfo.getBaseUriBuilder()
//                    .path(VisitController.class, "getVisits")
//                    .build()
//                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(TransactionalException ex){
            throw new NotFoundException();
        }
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN})
    public void update(Visit visit, UUID garageId) {
        try {
            Optional<Visit> visitOptional = visitService.findAll(garageId)
                    .stream().filter(v -> v.getId().equals(visit.getId()))
                    .findFirst();
            if(!visitOptional.isPresent()) {
                throw new NotFoundException();
            }
            else {
                Visit v = visitOptional.get();
                v.VIN = visit.VIN;
                v.date = visit.date;
                visitService.update(v);
            }
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN})
    public void delete(UUID id, UUID garageId) {
        visitService.findAll(garageId)
                .stream().filter(visit -> visit.getId().equals(id))
                        .findFirst()
                                .ifPresentOrElse(
                                        visit -> visitService.delete(id),
                                        () -> {
                                            throw new NotFoundException();
                                        }
                                );
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public VisitResponse findClientVisit(UUID id, UUID clientId) {
        isOwner(clientId);
        return visitService.find(id).map(visit -> VisitResponse.builder()
                .date(visit.getDate())
                .id(visit.getId())
                .VIN(visit.getVIN())
                .build()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public void createClientVisit(CreateVisitRequest visitRequest, UUID clientId) {
        isOwner(clientId);
        try {
            visitRequest.setClientId(clientId);
            visitService.create(visitRequest);
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(TransactionalException ex){
            throw new NotFoundException();
        }
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public void updateClientVisit(Visit visit, UUID clientId) {
        isOwner(clientId);
        try {
            visitService.update(visit);
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(TransactionalException ex){
            throw new NotFoundException();
        }
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public void deleteClientVisit(UUID id, UUID clientId) {
        isOwner(clientId);
        visitService.findAll(id)
                .stream().filter(visit -> visit.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        visit -> visitService.delete(id),
                        () -> {
                            throw new NotFoundException();
                        }
                );
    }

    private void isOwner(UUID clientId){
        if(!securityContext.isUserInRole(ClientRoles.ADMIN)){
            String login = clientService.find(clientId).map(client -> client.getLogin()).orElseThrow(NotFoundException::new);
            if(!securityContext.getUserPrincipal().getName().equals(login)) {
                throw new NotAuthorizedException("You Don't Have Permission");
            }
        }
    }
}
