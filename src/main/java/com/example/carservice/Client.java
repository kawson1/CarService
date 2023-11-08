package com.example.carservice;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Client implements Serializable {

    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthdate;

    private List<Visit> visitList;

    private ClientType clientType;

    private byte[] portrait;

}
