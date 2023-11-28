package com.example.carservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitRequest {
    public UUID id;
    public String VIN;
    public LocalDate date;
    public UUID garageId;
    public UUID clientId;
}
