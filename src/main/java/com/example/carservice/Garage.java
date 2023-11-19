package com.example.carservice;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "garages")
public class Garage implements Serializable{

    @Id
    public UUID id;

    public String title;

    public String city;

    public String zipCode;

    public int countEmployees;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "garage", cascade = CascadeType.REMOVE)
    public List<Visit> visits;
}
