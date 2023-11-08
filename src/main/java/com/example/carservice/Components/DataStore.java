package com.example.carservice.Components;

import com.example.carservice.Client;
import com.example.carservice.Garage;
import com.example.carservice.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    private final Set<Garage> garages = new HashSet<>();

    private final Set<Visit> visits = new HashSet<>();

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

    public synchronized void deleteClient(Client value) throws IllegalArgumentException{
        if(clients.removeIf(user -> user.getId().equals(value.getId())))
            return;
        else
            throw new IllegalArgumentException("The client id: " + value.getId() +" does not exists");
    }



    public synchronized List<Garage> findAllGarages(){
        return garages.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createGarage(Garage value) throws IllegalArgumentException{
        if(garages.stream().anyMatch(garage -> garage.getId().equals(value.getId()))){
            throw new IllegalArgumentException("Garage id: " + value.getId() +" is not unique");
        }
        garages.add(cloningUtility.clone(value));
    }

    public synchronized void updateGarage(Garage value) throws IllegalArgumentException {
        if (garages.removeIf(garage -> garage.getId().equals(value.getId()))) {
            garages.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The Garage id: " + value.getId() +" does not exists");
        }
    }

    public synchronized void deleteGarage(Garage value) throws IllegalArgumentException{
        if(garages.removeIf(garage -> garage.getId().equals(value.getId())))
            return;
        else
            throw new IllegalArgumentException("The garage id: " + value.getId() +" does not exists");
    }


    public synchronized List<Visit> findAllVisits(){
        return visits.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createVisit(Visit value) throws IllegalArgumentException{
        if(visits.stream().anyMatch(visit -> visit.getId().equals(value.getId()))){
            throw new IllegalArgumentException("Visit id: " + value.getId() +" is not unique");
        }
        visits.add(cloningUtility.clone(value));
    }

    public synchronized void updateVisit(Visit value) throws IllegalArgumentException {
        if (visits.removeIf(visit -> visit.getId().equals(value.getId()))) {
            System.out.println(value.getVIN());
            visits.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The Visit id: " + value.getId() +" does not exists");
        }
    }

    public synchronized void deleteVisit(Visit value) throws IllegalArgumentException{
        if(visits.removeIf(visit -> visit.getId().equals(value.getId())))
            return;
        else
            throw new IllegalArgumentException("The visit id: " + value.getId() +" does not exists");
    }

}
