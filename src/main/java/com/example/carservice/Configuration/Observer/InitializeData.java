package com.example.carservice.Configuration.Observer;

import com.example.carservice.Client;
import com.example.carservice.ClientType;
import com.example.carservice.Garage;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class InitializeData {

    private final ClientService clientService;

    private final GarageService garageService;

    private final VisitService visitService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializeData(ClientService clientService, GarageService garageService, VisitService visitService, RequestContextController requestContextController){
        this.clientService = clientService;
        this.garageService = garageService;
        this.visitService = visitService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
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

//        clientService.create(c1);
//        clientService.create(c2);
//        clientService.create(c3);
//        clientService.create(c4);

        Garage g1 = Garage.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a7"))
                .title("Pimp my car")
                .city("Gdansk")
                .zipCode("80-800")
                .countEmployees(0)
                .build();

        Garage g2 = Garage.builder()
                .id(UUID.randomUUID())
                .title("GK Warsztat Gdansk")
                .city("Gdansk")
                .zipCode("80-800")
                .countEmployees(0)
                .build();

        Garage g3 = Garage.builder()
                .id(UUID.randomUUID())
                .title("P4TR0L Workshop")
                .city("Gdansk")
                .zipCode("80-800")
                .countEmployees(0)
                .build();

//        garageService.create(g1);
//        garageService.create(g2);
//        garageService.create(g3);

        Visit v1 = Visit.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a8"))
                .date(LocalDate.of(2012,5,13))
                .VIN("4T1BF1FK9DU292392")
                .garage(g1)
                .client(c1)
                .build();

        Visit v2 = Visit.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.of(2014,11,24))
                .VIN("1GNDS13S532111299")
                .garage(g1)
                .client(c1)
                .build();

        Visit v3 = Visit.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.of(2016,8,5))
                .VIN("2B3HD46R31H659452")
                .garage(g3)
                .client(c2)
                .build();

        Visit v4 = Visit.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.of(2018,2,1))
                .VIN("1NXBU40EX9Z021701")
                .garage(g2)
                .client(c3)
                .build();

//        visitService.create(v1);
//        visitService.create(v2);
//        visitService.create(v3);
//        visitService.create(v4);

        requestContextController.deactivate();
    }

}
