package com.example.carservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Client implements Serializable {

    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthdate;

    private List<Visit> visitList;

    private ClientType clientType;

    private byte[] portrait;

}
