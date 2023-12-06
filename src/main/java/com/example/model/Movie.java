package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("Movie")
public class Movie extends LendableMaterial{

    @Enumerated(value = EnumType.STRING)
    private MovieGenre movieGenre;

    @ManyToOne
    @JsonBackReference
    private Director director;

    @ManyToOne
    @JsonBackReference(value = "screenwriter-movies")
    private Author screenwriter;
}
