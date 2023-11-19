package com.example.carservice;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Id
    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthdate;

    @OneToMany(mappedBy = "client")
    private List<Visit> visitList;

    @Enumerated(EnumType.STRING)
    private ClientType clientType = ClientType.NEW;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] portrait;

}
