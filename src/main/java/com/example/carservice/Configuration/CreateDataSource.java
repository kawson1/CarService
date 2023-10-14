package com.example.carservice.Configuration;

import com.example.carservice.Components.CloningUtility;
import com.example.carservice.Components.DataStore;
import com.example.carservice.Repositories.ClientInMemoryRepository;
import com.example.carservice.Repositories.ClientRepository;
import com.example.carservice.Services.ClientService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event){
        event.getServletContext().setAttribute("dataSource", new DataStore(new CloningUtility()));
    }

}

