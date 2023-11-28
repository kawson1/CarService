package com.example.carservice.Configuration.Observer;

import com.example.carservice.*;
import com.example.carservice.Services.ClientService;
import com.example.carservice.Services.GarageService;
import com.example.carservice.Services.VisitService;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
public class InitializeData {

    private ClientService clientService;

    private GarageService garageService;

    private VisitService visitService;

    @EJB
    public void setClientService(ClientService service) { this.clientService = service; }

    @EJB
    public void setGarageService(GarageService service) { this.garageService = service; }

    @EJB
    public void setVisitService(VisitService service) { this.visitService = service; }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (clientService.findByLogin("admin").isEmpty()) {
            Client c1 = Client.builder()
                    .login("admin")
                    .password("admin")
                    .name("System")
                    .surname("Admin")
                    .birthdate(LocalDate.of(1990, 10, 21))
                    .clientType(ClientType.VIP)
                    .roles(List.of(ClientRoles.ADMIN, ClientRoles.USER))
                    .build();

            Client c2 = Client.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .login("klient2")
                    .password("klient2")
                    .name("Klient2")
                    .surname("Nazwisko2")
                    .birthdate(LocalDate.of(1990, 10, 21))
                    .clientType(ClientType.VIP)
                    .roles(List.of(ClientRoles.USER))
                    .build();

            Client c3 = Client.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a7"))
                    .login("klient3")
                    .password("klient3")
                    .name("Klient3")
                    .surname("Nazwisko3")
                    .birthdate(LocalDate.of(1990, 10, 21))
                    .clientType(ClientType.ATTENDING)
                    .roles(List.of(ClientRoles.USER))
                    .build();

            Client c4 = Client.builder()
                    .login("klient4")
                    .password("klient4")
                    .name("Klient4")
                    .surname("Nazwisko4")
                    .birthdate(LocalDate.of(1990, 10, 21))
                    .clientType(ClientType.NEW)
                    .roles(List.of(ClientRoles.USER))
                    .build();

            clientService.create(c1);
            clientService.create(c2);
            clientService.create(c3);
            clientService.create(c4);

            Garage g1 = Garage.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .title("Pimp my car")
                    .city("Gdansk")
                    .zipCode("80-800")
                    .countEmployees(0)
                    .build();

            Garage g2 = Garage.builder()
                    .title("GK Warsztat Gdansk")
                    .city("Gdansk")
                    .zipCode("80-800")
                    .countEmployees(0)
                    .build();

            Garage g3 = Garage.builder()
                    .title("P4TR0L Workshop")
                    .city("Gdansk")
                    .zipCode("80-800")
                    .countEmployees(0)
                    .build();

            garageService.create(g1);
            garageService.create(g2);
            garageService.create(g3);

            Visit v1 = Visit.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .date(LocalDate.of(2012,5,13))
                    .VIN("4T1BF1FK9DU292392")
                    .garage(g1)
                    .client(c1)
                    .build();

            Visit v2 = Visit.builder()
                    .date(LocalDate.of(2014,11,24))
                    .VIN("1GNDS13S532111299")
                    .garage(g1)
                    .client(c1)
                    .build();

            Visit v3 = Visit.builder()
                    .date(LocalDate.of(2016,8,5))
                    .VIN("2B3HD46R31H659452")
                    .garage(g3)
                    .client(c2)
                    .build();

            Visit v4 = Visit.builder()
                    .date(LocalDate.of(2018,2,1))
                    .VIN("1NXBU40EX9Z021701")
                    .garage(g2)
                    .client(c3)
                    .build();

            visitService.create(v1);
            visitService.create(v2);
            visitService.create(v3);
            visitService.create(v4);

        }
    }

}

