package com.example.carservice.View.Converter;

import com.example.carservice.Client;
import com.example.carservice.Garage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.util.UUID;

@FacesConverter("clientConverter")
public class ClientConverter implements Converter<Client> {

    @Override
    public Client getAsObject(FacesContext context, UIComponent component, String value) {
        return Client.builder().id(UUID.fromString(value)).build();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Client value) {
        return value.getId().toString();
    }

}
