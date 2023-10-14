package com.example.carservice.Configuration;

import com.example.carservice.Components.DataStore;
import com.example.carservice.Controllers.ClientController;
import com.example.carservice.Repositories.ClientInMemoryRepository;
import com.example.carservice.Repositories.ClientRepository;
import com.example.carservice.Services.ClientService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event){
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("dataSource");

        ClientRepository clientRepository = new ClientInMemoryRepository(dataSource);
        event.getServletContext().setAttribute("clientService", new ClientService(clientRepository));
    }

}

