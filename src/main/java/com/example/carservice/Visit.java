package com.example.carservice;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Visit implements Serializable {

    public UUID id;
    public LocalDate date;
    public String VIN;
    public Garage garage;
    public Client client;
    public List<Fault> faults;
}
