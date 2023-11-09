package com.example.carservice.Controllers;

import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Garage;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("")
public class VisitControllerImplementation implements VisitController {

    private final VisitService visitService;
    private final GarageService garageService;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    @Inject
    public VisitControllerImplementation(
            VisitService visitService,
            GarageService garageService,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.visitService = visitService;
        this.garageService = garageService;
        this.uriInfo = uriInfo;
    }

    @Override
    public Visit find(UUID id, UUID garageId) {
        return visitService.findAll(garageId)
                .stream().filter(visit -> visit.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Visit> getVisits() {
        return visitService.findAll();
    }

    @Override
    public List<Visit> getVisits(UUID garageId) {
        return visitService.findAll(garageId);
    }

    @Override
    public void create(Visit visit, UUID garageId) {
        System.out.println(garageId.toString());
        try {
            Garage garage = garageService.find(garageId).orElseThrow(NotFoundException::new);
            Visit visit_ = Visit.builder()
                    .id(UUID.randomUUID())
                    .VIN(visit.getVIN())
                    .date(visit.getDate())
                    .garage(garage)
                    .build();
            visitService.create(visit_);
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(VisitController.class, "find")
                    .build(garageId, visit_.getId())
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch(IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void update(Visit visit, UUID garageId) {
        try {
            Optional<Visit> visitOptional = visitService.findAll(garageId)
                    .stream().filter(v -> v.getId().equals(visit.getId()))
                    .findFirst();
//                    .ifPresentOrElse(
//                            visit1 -> visitService.update(visit),
//                            ()->{
//                                throw new NotFoundException();
//                            });
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
    public void delete(UUID id, UUID garageId) {
        visitService.findAll(garageId)
                .stream().filter(visit -> visit.getId().equals(id))
                        .findFirst()
                                .ifPresentOrElse(
                                        visit -> visitService.delete(id),
                                        () -> {
                                            System.out.println("EXCE");
                                            throw new NotFoundException();
                                        }
                                );
    }
}
