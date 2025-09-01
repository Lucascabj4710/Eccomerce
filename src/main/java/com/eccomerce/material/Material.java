package com.eccomerce.material;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

}
