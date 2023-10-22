package com.example.carservice.Controllers;

import com.example.carservice.Client;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface ClientController {

    Client find(UUID id);

    Client find(String name);

    List<Client> getClients();

    void create(Client client);

    void update(Client client);

    void delete(UUID id);

    byte[] getPortrait(UUID id);

    void putClientPortrait(UUID id, InputStream portrait);

    void deleteClientPortrait(UUID id);
}
