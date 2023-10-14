package com.example.carservice.Configuration;

import com.example.carservice.Controllers.ClientController;
import com.example.carservice.Services.ClientService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event){
//        ClientService clientService = (ClientService) event.getServletContext().getAttribute("clientService");
//
//        ServletContext sc = event.getServletContext();
//        sc.setAttribute("clientController", new ClientController(clientService));

        ClientService clientService = (ClientService) event.getServletContext().getAttribute("clientService");

        event.getServletContext().setAttribute("clientController", new ClientController(clientService));
    }

}
