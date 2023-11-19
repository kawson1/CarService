package com.example.carservice.dto;

import com.example.carservice.ClientType;
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
public class ClientResponse {
    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthdate;

    private ClientType clientType;
}
