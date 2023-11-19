package com.example.carservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VisitResponse {
    public UUID id;
    public LocalDate date;
    public String VIN;
}
