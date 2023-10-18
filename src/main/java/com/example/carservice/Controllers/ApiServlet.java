package com.example.carservice.Controllers;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final ClientController clientController;

    public static final class Paths{
        public static final String API = "/api";
    }

    public static final class Patterns{

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern CLIENT = Pattern.compile("/clients/(%s)".formatted(UUID.pattern()));

        public static final Pattern CLIENTS = Pattern.compile("/clients/?");

        public static final Pattern CLIENT_PORTRAIT = Pattern.compile("/clients/(%s)/portrait".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(ClientController clientController) {
        this.clientController = clientController;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();

        if(!servletPath.equals(Paths.API)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else if(path.matches(Patterns.CLIENTS.pattern())){
            response.setContentType("application/json");
            String clientsJsonString = jsonb.toJson(clientController.getClients());
            response.getWriter().write(clientsJsonString);
        }
        else if(path.matches(Patterns.CLIENT.pattern())){
            response.setContentType("application/json");
            UUID uuid = extractUuid(Patterns.CLIENT, path);
            String clientsJsonString = jsonb.toJson(clientController.find(uuid));
            response.getWriter().write(clientsJsonString);
            return;
        }
        else if(path.matches(Patterns.CLIENT_PORTRAIT.pattern())){
            response.setContentType("image/png");
            UUID id = extractUuid(Patterns.CLIENT_PORTRAIT, path);
            byte[] portrait = clientController.getPortrait(id);
            response.setContentLength(portrait.length);
            response.getOutputStream().write(portrait);
        }
        else
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(!Paths.API.equals(servletPath))
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        if(path.matches(Patterns.CLIENT_PORTRAIT.pattern())){
            UUID uuid = extractUuid(Patterns.CLIENT_PORTRAIT, path);
            clientController.putClientPortrait(uuid, request.getPart("portrait").getInputStream());
            return;
        }
        else
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(!Paths.API.equals(servletPath))
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        if(path.matches(Patterns.CLIENT_PORTRAIT.pattern())){
            UUID uuid = extractUuid(Patterns.CLIENT_PORTRAIT, path);
            clientController.deleteClientPortrait(uuid);
            return;
        }
        else
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path == null ? "" : path;
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

}
