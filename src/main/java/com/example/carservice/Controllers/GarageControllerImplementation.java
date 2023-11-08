package com.example.carservice.Controllers;

import com.example.carservice.Garage;
import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Controllers.Exception.NotFoundException;
import com.example.carservice.Services.GarageService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Path("")
@Log
public class GarageControllerImplementation implements GarageController{

    private final GarageService garageService;

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
    public GarageControllerImplementation(
            GarageService garageService,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.garageService = garageService;
        this.uriInfo = uriInfo;
    }

    public Garage find(UUID id){
        return garageService.find(id)
                .orElseThrow(NotFoundException::new);
    }

    public Garage find(String name){
        return garageService.find(name)
                .orElseThrow(NotFoundException::new);
    }

    public List<Garage> getGarages(){
        return garageService.findAll();
    }

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

    public void update(Garage garage){
        try {
            garageService.update(garage);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    public void delete(UUID id){
        garageService.find(id).ifPresentOrElse(
                entity -> garageService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
