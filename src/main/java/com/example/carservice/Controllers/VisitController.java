package com.example.carservice.Controllers;

import com.example.carservice.Visit;
import com.example.carservice.dto.VisitResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("")
public interface VisitController {

    @GET
    @Path("/garages/{garageId}/visits/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Visit find(@PathParam("id") UUID id, @PathParam("garageId") UUID garageId);

    @GET
    @Path("/visits")
    @Produces(MediaType.APPLICATION_JSON)
    List<VisitResponse> getVisits();

    @GET
    @Path("/garages/{garageId}/visits")
    @Produces(MediaType.APPLICATION_JSON)
    List<VisitResponse> getGarageVisits(@PathParam("garageId") UUID garageId);

    @POST
    @Path("/garages/{garageId}/visits")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(Visit visit, @PathParam("garageId") UUID garageId);

    @PUT
    @Path("/garages/{garageId}/visits")
    @Consumes({MediaType.APPLICATION_JSON})
    void update(Visit visit, @PathParam("garageId") UUID garageId);

    @DELETE
    @Path("/garages/{garageId}/visits/{id}")
    void delete(@PathParam("id") UUID id, @PathParam("garageId") UUID garageId);

}
