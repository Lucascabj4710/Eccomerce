package com.eccomerce.address;

import com.eccomerce.client.entity.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String street;

    @Column(name = "number_street", nullable = false)
    private String numberStreet;

    private String department;

    private String floor;

    @Column(nullable = false)
    private String game;

    @Column(nullable = false)
    private String province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Client client;

}
