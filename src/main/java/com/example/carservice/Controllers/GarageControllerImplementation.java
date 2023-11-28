package com.example.carservice.Controllers;

import com.example.carservice.ClientRoles;
import com.example.carservice.Garage;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.dto.GarageResponse;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.*;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Path("")
@Log
@DeclareRoles({ClientRoles.ADMIN, ClientRoles.USER})
public class GarageControllerImplementation implements GarageController{

    private GarageService garageService;

    private VisitService visitService;

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
    public GarageControllerImplementation(@SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) { this.uriInfo = uriInfo; }

    @EJB
    public void setGarageService(GarageService service) { this.garageService = service; }

    @EJB
    public void setVisitService(VisitService service) { this.visitService = service; }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public GarageResponse find(UUID id){
        return garageService.find(id)
                .map(
                    garage -> GarageResponse.builder()
                    .city(garage.getCity())
                    .title(garage.getTitle())
                    .id(garage.getId())
                    .countEmployees(garage.getCountEmployees())
                    .zipCode(garage.getZipCode())
                    .build())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed({ClientRoles.ADMIN, ClientRoles.USER})
    public List<GarageResponse> getGarages(){
        return garageService.findAll()
                .stream()
                .map(garage -> GarageResponse.builder()
                        .city(garage.getCity())
                        .title(garage.getTitle())
                        .id(garage.getId())
                        .countEmployees(garage.getCountEmployees())
                        .zipCode(garage.getZipCode())
                        .build())
                .toList();
    }


    @Override
    @RolesAllowed(ClientRoles.ADMIN)
    public void create(Garage garage){
        try {
            garageService.create(garage);
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(GarageController.class, "find")
                    .build(garage.getId())
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    @RolesAllowed(ClientRoles.ADMIN)
    public void update(Garage garage){
        try {
            garageService.update(garage);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    @RolesAllowed(ClientRoles.ADMIN)
    public void delete(UUID id){
        garageService.find(id).ifPresentOrElse(
                entity -> garageService.delete(id),
                () ->
                {
                    throw new NotFoundException();
                }
        );
    }
}
