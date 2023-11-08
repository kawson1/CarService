package com.example.carservice.Controllers;

import com.example.carservice.Client;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Path("")
public interface ClientController {

    @GET
    @Path("/clients/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Client find(@PathParam("id") UUID id);

    Client find(String name);

    @GET
    @Path("/clients")
    @Produces(MediaType.APPLICATION_JSON)
    List<Client> getClients();

    @POST
    @Path("/clients")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(Client client);

    @PUT
    @Path("/clients")
    @Consumes({MediaType.APPLICATION_JSON})
    void update(Client client);

    @DELETE
    @Path("/clients/{id}")
    void delete(@PathParam("id") UUID id);

    @GET
    @Path("/clients/{id}/portrait")
    @Produces("image/png")
    byte[] getPortrait(@PathParam("id") UUID id);

    @PUT
    @Path("/clients/{id}/portrait")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putClientPortrait(UUID id, InputStream portrait);

    void deleteClientPortrait(UUID id);
}
