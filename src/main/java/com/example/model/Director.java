package com.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Director extends Person{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "director")
    @JsonManagedReference
    private List<Movie> movies;
}
