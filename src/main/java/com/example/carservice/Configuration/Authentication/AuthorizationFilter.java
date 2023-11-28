package com.example.carservice.Configuration.Authentication;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.security.Principal;

public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        Principal principal = containerRequestContext.getSecurityContext().getUserPrincipal();
        System.out.println(principal.getName());
        containerRequestContext.setSecurityContext(containerRequestContext.getSecurityContext());
    }
}
