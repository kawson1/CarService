package com.example.carservice.Components;

import com.example.carservice.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Force makes that every final property will be set null / 0 / false
 */
@NoArgsConstructor(force = true)
@ApplicationScoped
public class DataStore {

    private final Set<Client> clients = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Client> findAllClients(){
        return clients.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createClient(Client value) throws IllegalArgumentException{
        if(clients.stream().anyMatch(user -> user.getId().equals(value.getId()))){
            throw new IllegalArgumentException("The client id: " + value.getId() +" is not unique");
        }
        clients.add(cloningUtility.clone(value));
    }

    public synchronized void updateClient(Client value) throws IllegalArgumentException {
        if (clients.removeIf(user -> user.getId().equals(value.getId()))) {
            clients.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The client id: " + value.getId() +" does not exists");
        }
    }


}
