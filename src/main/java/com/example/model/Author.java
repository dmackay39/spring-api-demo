package com.example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Author.class)
public class Author extends Person{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books;

    @OneToMany(mappedBy = "screenwriter")
    @JsonManagedReference(value = "screenwriter-movies")
    private List<Movie> movies;
}
