package com.example.carservice.Configuration.Observer;

import com.example.carservice.Client;
import com.example.carservice.ClientType;
import com.example.carservice.Services.ClientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializeData {

    private ClientService clientService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializeData(ClientService clientService, RequestContextController requestContextController){
        this.clientService = clientService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private void init(){
        requestContextController.activate();

        Client c1 = Client.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .name("Klient1")
                .surname("Nazwisko1")
                .birthdate(LocalDate.of(1990,10,21))
                .clientType(ClientType.NEW)
                .build();

        Client c2 = Client.builder()
                .id(UUID.randomUUID())
                .name("Klient2")
                .surname("Nazwisko2")
                .birthdate(LocalDate.of(1990,10,21))
                .clientType(ClientType.VIP)
                .build();

        Client c3 = Client.builder()
                .id(UUID.randomUUID())
                .name("Klient3")
                .surname("Nazwisko3")
                .birthdate(LocalDate.of(1990,10,21))
                .clientType(ClientType.ATTENDING)
                .build();

        Client c4 = Client.builder()
                .id(UUID.randomUUID())
                .name("Klient4")
                .surname("Nazwisko4")
                .birthdate(LocalDate.of(1990,10,21))
                .clientType(ClientType.NEW)
                .build();

        clientService.create(c1);
        clientService.create(c2);
        clientService.create(c3);
        clientService.create(c4);

        requestContextController.deactivate();
    }

}
