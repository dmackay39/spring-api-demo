package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "material_type", discriminatorType = DiscriminatorType.STRING)
public class LendableMaterial {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private Boolean isAvailable;
}
