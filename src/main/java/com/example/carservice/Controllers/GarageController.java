package com.example.carservice.Controllers;

import com.example.carservice.ClientRoles;
import com.example.carservice.Garage;
import com.example.carservice.dto.GarageResponse;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("")
public interface GarageController {

    @GET
    @Path("/garages/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GarageResponse find(@PathParam("id") UUID id);

    @GET
    @Path("/garages")
    @Produces(MediaType.APPLICATION_JSON)
    List<GarageResponse> getGarages();

    @POST
    @Path("/garages")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(Garage garage);

    @PUT
    @Path("/garages")
    @Consumes({MediaType.APPLICATION_JSON})
    void update(Garage garage);

    @DELETE
    @Path("/garages/{id}")
    void delete(@PathParam("id") UUID id);

}
