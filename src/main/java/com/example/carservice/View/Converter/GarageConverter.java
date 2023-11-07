package com.example.carservice.View.Converter;

import com.example.carservice.Garage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.util.UUID;

@FacesConverter("garageConverter")
public class GarageConverter implements Converter<Garage> {

    @Override
    public Garage getAsObject(FacesContext context, UIComponent component, String value) {
        return Garage.builder().id(UUID.fromString(value)).build();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Garage value) {
        return value.id.toString();
    }

}
