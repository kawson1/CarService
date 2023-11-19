package com.example.carservice;

import jakarta.persistence.*;
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
@Entity
@ToString
@Table(name = "visits")
public class Visit implements Serializable {

    @Id
    public UUID id;

    public LocalDate date;

    public String VIN;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    @ToString.Exclude
    public Garage garage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    public Client client;
}
