package com.example.carservice.Controllers;

import com.example.carservice.Controllers.Exception.BadRequestException;
import com.example.carservice.Garage;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import com.example.carservice.dto.VisitResponse;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
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
                                            throw new NotFoundException();
                                        }
                                );
    }
}
