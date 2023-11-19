package com.example.carservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GarageResponse {
    public UUID id;

    public String title;

    public String city;

    public String zipCode;

    public int countEmployees;
}
