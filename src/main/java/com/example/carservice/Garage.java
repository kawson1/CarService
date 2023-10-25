package com.example.carservice;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Garage implements Serializable{

    public UUID id;
    public String title;
    public String city;
    public String zipCode;
    public int countEmployees;
    public List<Visit> visits;
}
